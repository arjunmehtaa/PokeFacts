package com.arjuj.domain.usecases

import com.arjuj.domain.model.PokemonTypeResults
import com.arjuj.domain.repository.RepositoryInterface

class GetAllPokemonOfTypeUseCase(private val repository: RepositoryInterface) {
    suspend fun getAllPokemonOfType(type: String): PokemonTypeResults = repository.getPokemonTypeList(type)
}