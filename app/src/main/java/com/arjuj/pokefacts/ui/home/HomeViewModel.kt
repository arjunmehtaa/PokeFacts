package com.arjuj.pokefacts.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjuj.data.utils.Constants
import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.model.PokemonResults
import com.arjuj.domain.model.PokemonTypeResults
import com.arjuj.domain.usecases.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class HomeViewModel(
    private val getPokemonUseCase: GetPokemonUseCase,
    private val addFavoritePokemonUseCase: AddFavoritePokemonUseCase,
    private val addPokemonUseCase: AddPokemonUseCase,
    private val removeFavoritePokemonUseCase: RemoveFavoritePokemonUseCase,
    private val getIsPokemonFavoriteUseCase: GetIsPokemonFavoriteUseCase,
    private val getAllPokemonOfTypeUseCase: GetAllPokemonOfTypeUseCase,
    private val getAllPokemonNamesUseCase: GetAllPokemonNamesUseCase
) : ViewModel(), KoinComponent {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    private var job: Job = Job()
    val list: MutableList<Pokemon> = mutableListOf()

    private val _myPokemon: MutableLiveData<Result<MutableList<Pokemon>>> = MutableLiveData()
    val myPokemon: LiveData<Result<MutableList<Pokemon>>>
        get() = _myPokemon

    private val _myTypePokemon: MutableLiveData<Result<PokemonTypeResults>> = MutableLiveData()
    val myTypePokemon: LiveData<Result<PokemonTypeResults>>
        get() = _myTypePokemon

    private val _myPokemonNamesList: MutableLiveData<Result<PokemonResults>> = MutableLiveData()
    val myPokemonNamesList: LiveData<Result<PokemonResults>>
        get() = _myPokemonNamesList

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            _myPokemon.value = Result.Failure(exception)
        }
        pokemonsDisplayed = 0
        getAllPokemonNames()
    }

    fun getPokemon(pokemonList: MutableList<Pokemon>) {
        cancelJobIfRunning()
        job = viewModelScope.launch(coroutineExceptionHandler) {
            _myPokemon.value = Result.Loading
            coroutineScope {
                pokemonList.forEach {
                    launch(coroutineExceptionHandler) {
                        if (!checkIfContainsPokemon(list, it)) {
                            val pokemon = getPokemonUseCase.getPokemon(it.id)
                            list.add(pokemon)
                            viewModelScope.launch {
                                addPokemonUseCase.addPokemon(pokemon)
                            }
                        }
                    }
                }
            }
            list.sortBy { it.id }
            _myPokemon.value = Result.Success(list)
        }
    }

    fun addFavoritePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            addFavoritePokemonUseCase.addFavoritePokemon(pokemon)
        }
    }

    fun deleteFavoritePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            removeFavoritePokemonUseCase.removeFavoritePokemon(pokemon)
        }
    }

    fun isPokemonFavorite(id: Int): Boolean {
        return getIsPokemonFavoriteUseCase.isPokemonFavorite(id)
    }

    fun getPokemonOfType(type : String) {
        cancelJobIfRunning()
        job = viewModelScope.launch(coroutineExceptionHandler) {
            _myTypePokemon.value = Result.Loading
            _myTypePokemon.value = Result.Success(getAllPokemonOfTypeUseCase.getAllPokemonOfType(type))
        }
    }

    fun getAllPokemonNames() {
        cancelJobIfRunning()
        job = viewModelScope.launch(coroutineExceptionHandler) {
            _myPokemonNamesList.value = Result.Loading
            _myPokemonNamesList.value =
                Result.Success(getAllPokemonNamesUseCase.getAllPokemonNames(Constants.TOTAL_POKEMONS))
        }
    }

    private fun checkIfContainsPokemon(pokemonList: MutableList<Pokemon>, pokemon: Pokemon): Boolean {
        pokemonList.forEach {
            if (it.id == pokemon.id) return true
        }
        return false
    }

    private fun cancelJobIfRunning() {
        if (job.isActive) {
            job.cancel()
        }
    }

    sealed class Result<out T : Any> {
        data class Success<out T : Any>(val value: T) : Result<T>()
        object Loading : Result<Nothing>()
        data class Failure(val throwable: Throwable) : Result<Nothing>()
    }
}
