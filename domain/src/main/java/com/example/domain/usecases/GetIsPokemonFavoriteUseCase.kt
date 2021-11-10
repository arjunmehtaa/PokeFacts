package com.example.domain.usecases

import com.example.domain.repository.RoomRepositoryInterface

class GetIsPokemonFavoriteUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    fun isPokemonFavorite(id : Int) : Boolean{
        return roomRepositoryInterface.isPokemonFavorite(id)
    }
}