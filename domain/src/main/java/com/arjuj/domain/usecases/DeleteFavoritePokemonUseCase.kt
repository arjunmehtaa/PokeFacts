package com.arjuj.domain.usecases

import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.repository.RoomRepositoryInterface

class DeleteFavoritePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun deleteFavoritePokemon(pokemon : Pokemon){
        roomRepositoryInterface.deletePokemon(pokemon)
    }
}