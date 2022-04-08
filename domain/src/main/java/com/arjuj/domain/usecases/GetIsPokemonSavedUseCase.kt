package com.arjuj.domain.usecases

import com.arjuj.domain.repository.RoomRepositoryInterface

class GetIsPokemonSavedUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    fun isPokemonSaved(id: Int): Boolean {
        return roomRepositoryInterface.isPokemonSaved(id)
    }
}