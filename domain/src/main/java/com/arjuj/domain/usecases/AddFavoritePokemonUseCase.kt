package com.arjuj.domain.usecases

import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.repository.RoomRepositoryInterface

class AddFavoritePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun addFavoritePokemon(pokemon : Pokemon){
        roomRepositoryInterface.addFavoritePokemon(pokemon)
    }
}