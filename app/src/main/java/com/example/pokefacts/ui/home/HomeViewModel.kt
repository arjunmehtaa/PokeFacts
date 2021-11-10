package com.example.pokefacts.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.Constants
import com.example.domain.model.Pokemon
import com.example.domain.usecases.*
import kotlinx.coroutines.*
import org.koin.core.KoinComponent

class HomeViewModel(
    private val getPokemonUseCase: GetPokemonUseCase,
    private val addFavoritePokemonUseCase: AddFavoritePokemonUseCase,
    private val deleteFavoritePokemonUseCase: DeleteFavoritePokemonUseCase,
    private val getIsPokemonFavoriteUseCase: GetIsPokemonFavoriteUseCase
) : ViewModel(), KoinComponent {

    private var coroutineExceptionHandler : CoroutineExceptionHandler
    private val _myPokemon: MutableLiveData<Result<MutableList<Pokemon>>> = MutableLiveData()
    val myPokemon: LiveData<Result<MutableList<Pokemon>>>
        get() = _myPokemon

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            _myPokemon.value = Result.Failure(exception)
        }
        pokemonsDisplayed = 0
        getPokemon(Constants.POKEMONS_LOAD_LIMIT, 0)
    }

    fun getPokemon(numberOfPokemons: Int, offset: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _myPokemon.value = Result.Loading
            _myPokemon.value = Result.Success(getPokemonUseCase.getPokemon(numberOfPokemons, offset))
        }
    }

    fun addFavoritePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            addFavoritePokemonUseCase.addFavoritePokemon(pokemon)
        }
    }

    fun deleteFavoritePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            deleteFavoritePokemonUseCase.deleteFavoritePokemon(pokemon)
        }
    }

    fun isPokemonFavorite(id: Int): Boolean {
        return getIsPokemonFavoriteUseCase.isPokemonFavorite(id)
    }

    sealed class Result<out T : Any> {
        data class Success<out T : Any>(val value: T) : Result<T>()
        object Loading : Result<Nothing>()
        data class Failure(val throwable: Throwable) : Result<Nothing>()
    }
}
