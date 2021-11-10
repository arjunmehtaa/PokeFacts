package com.example.data.repository

import com.example.data.api.RetrofitInstance
import com.example.domain.model.Pokemon
import com.example.domain.model.Species
import com.example.domain.repository.RepositoryInterface

class Repository : RepositoryInterface {
    override suspend fun getPokemon(id: Int): Pokemon {
        return RetrofitInstance.api.getPokemon(id)
    }

    override suspend fun getSpecies(id: Int): Species {
        return RetrofitInstance.api.getSpecies(id)
    }
}