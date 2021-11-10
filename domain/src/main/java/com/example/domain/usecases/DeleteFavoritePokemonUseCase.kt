package com.example.domain.usecases

import com.example.domain.model.Pokemon
import com.example.domain.repository.RoomRepositoryInterface

class DeleteFavoritePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun deleteFavoritePokemon(pokemon : Pokemon){
        roomRepositoryInterface.deletePokemon(pokemon)
    }
}