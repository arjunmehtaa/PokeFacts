package com.arjuj.domain.usecases

import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.repository.RoomRepositoryInterface

class AddPokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun addPokemon(pokemon: Pokemon) {
        roomRepositoryInterface.addPokemon(pokemon)
    }
}