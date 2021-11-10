package com.example.domain.usecases

import com.example.domain.model.Pokemon
import com.example.domain.repository.RoomRepositoryInterface

class AddFavoritePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun addFavoritePokemon(pokemon : Pokemon){
        roomRepositoryInterface.addFavoritePokemon(pokemon)
    }
}