package com.arjuj.data.room

import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.repository.RoomRepositoryInterface

class RoomPokemonRepository(private val pokemonDao: RoomPokemonDao) : RoomRepositoryInterface {

    private val pokemonItemConverter = PokemonItemConverter()

    override fun getFavoritePokemonList(): List<Pokemon> {
        return pokemonDao.readFavoriteItems().map {
            pokemonItemConverter.roomPokemonToPokemon(it)
        }
    }

    override fun getAllPokemonList(): List<Pokemon> {
        return pokemonDao.readFavoriteItems().map {
            pokemonItemConverter.roomPokemonToPokemon(it)
        }
    }

    override fun getSinglePokemon(id: Int): Pokemon {
        return pokemonItemConverter.roomPokemonToPokemon(pokemonDao.readSingleItem(id))
    }

    override suspend fun addFavoritePokemon(pokemon: Pokemon) {
        return pokemonDao.addFavoritePokemon(
            pokemonItemConverter.pokemonToRoomPokemon(
                pokemon,
                true
            )
        )
    }

    override suspend fun removeFavoritePokemon(pokemon: Pokemon) {
        pokemonDao.removeFavoritePokemon(pokemonItemConverter.pokemonToRoomPokemon(pokemon, false))
    }

    override suspend fun addPokemon(pokemon: Pokemon) {
        return pokemonDao.addRoomPokemonItem(pokemonItemConverter.pokemonToRoomPokemon(pokemon))
    }

    override fun isPokemonFavorite(id: Int): Boolean {
        return pokemonDao.isPokemonFavorite(id)
    }

    override fun isPokemonSaved(id: Int): Boolean {
        return pokemonDao.isPokemonSaved(id)
    }
}