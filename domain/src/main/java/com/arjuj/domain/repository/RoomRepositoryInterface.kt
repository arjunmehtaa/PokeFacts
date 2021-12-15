package com.arjuj.domain.repository

import com.arjuj.domain.model.Pokemon

interface RoomRepositoryInterface {
    fun getFavoritePokemonList() : List<Pokemon>
    suspend fun addFavoritePokemon(pokemon : Pokemon)
    fun isPokemonFavorite(id : Int) : Boolean
    suspend fun deletePokemon(pokemon: Pokemon)
}