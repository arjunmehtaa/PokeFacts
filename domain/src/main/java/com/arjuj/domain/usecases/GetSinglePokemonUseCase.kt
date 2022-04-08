package com.arjuj.domain.usecases

import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.repository.RoomRepositoryInterface

class GetSinglePokemonUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    fun getSinglePokemon(id: Int): Pokemon = roomRepositoryInterface.getSinglePokemon(id)
}