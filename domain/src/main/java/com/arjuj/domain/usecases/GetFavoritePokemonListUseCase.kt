package com.arjuj.domain.usecases

import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.repository.RoomRepositoryInterface

class GetFavoritePokemonListUseCase(private val roomRepositoryInterface: RoomRepositoryInterface) {

    suspend fun getFavoritePokemonList() : List<Pokemon> = roomRepositoryInterface.getFavoritePokemonList()
}