package com.arjuj.domain.repository

import com.arjuj.domain.model.Pokemon

interface RoomRepositoryInterface {
    fun getFavoritePokemonList(): List<Pokemon>
    fun getAllPokemonList(): List<Pokemon>
    fun getSinglePokemon(id: Int): Pokemon
    suspend fun addFavoritePokemon(pokemon: Pokemon)
    suspend fun removeFavoritePokemon(pokemon: Pokemon)
    suspend fun addPokemon(pokemon: Pokemon)
    fun isPokemonFavorite(id: Int): Boolean
    fun isPokemonSaved(id: Int): Boolean
}