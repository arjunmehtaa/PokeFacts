package com.arjuj.domain.usecases

import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.repository.RoomRepositoryInterface

class RemoveFavoritePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun removeFavoritePokemon(pokemon: Pokemon) {
        roomRepositoryInterface.removeFavoritePokemon(pokemon)
    }
}