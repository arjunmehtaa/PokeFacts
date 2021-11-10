package com.example.domain.usecases

import com.example.domain.model.Pokemon
import com.example.domain.repository.RoomRepositoryInterface

class GetFavoritePokemonListUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun getFavoritePokemonList() : List<Pokemon> = roomRepositoryInterface.getFavoritePokemonList()
}