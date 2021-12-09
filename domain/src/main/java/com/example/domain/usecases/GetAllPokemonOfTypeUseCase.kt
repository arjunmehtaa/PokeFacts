package com.example.domain.usecases

import com.example.domain.model.PokemonTypeResults
import com.example.domain.repository.RepositoryInterface

class GetAllPokemonOfTypeUseCase(private val repository: RepositoryInterface) {
    suspend fun getAllPokemonOfType(type: String): PokemonTypeResults = repository.getPokemonTypeList(type)
}