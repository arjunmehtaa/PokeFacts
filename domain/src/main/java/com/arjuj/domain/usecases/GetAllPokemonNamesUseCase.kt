package com.arjuj.domain.usecases

import com.arjuj.domain.model.PokemonResults
import com.arjuj.domain.repository.RepositoryInterface

class GetAllPokemonNamesUseCase(private val repository: RepositoryInterface) {
    suspend fun getAllPokemonNames(limit: Int): PokemonResults = repository.getPokemonList(limit)
}