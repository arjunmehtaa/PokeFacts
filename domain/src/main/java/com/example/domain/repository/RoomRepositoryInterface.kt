package com.example.domain.repository

import com.example.domain.model.Pokemon

interface RoomRepositoryInterface {
    fun getFavoritePokemonList() : List<Pokemon>
    suspend fun addFavoritePokemon(pokemon : Pokemon)
    fun isPokemonFavorite(id : Int) : Boolean
    suspend fun deletePokemon(pokemon: Pokemon)
}