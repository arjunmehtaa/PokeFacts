package com.arjuj.domain.usecases

import com.arjuj.domain.repository.RoomRepositoryInterface

class GetIsPokemonFavoriteUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    fun isPokemonFavorite(id : Int) : Boolean{
        return roomRepositoryInterface.isPokemonFavorite(id)
    }
}