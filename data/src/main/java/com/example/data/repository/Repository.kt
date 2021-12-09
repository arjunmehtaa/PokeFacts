package com.example.data.repository

import com.example.data.api.RetrofitInstance
import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonResults
import com.example.domain.model.PokemonTypeResults
import com.example.domain.model.Species
import com.example.domain.repository.RepositoryInterface

class Repository : RepositoryInterface {
    override suspend fun getPokemon(id: Int): Pokemon {
        return RetrofitInstance.api.getPokemon(id)
    }

    override suspend fun getSpecies(id: Int): Species {
        return RetrofitInstance.api.getSpecies(id)
    }

    override suspend fun getPokemonList(limit: Int): PokemonResults {
        return RetrofitInstance.api.getPokemonList(limit)
    }

    override suspend fun getPokemonTypeList(type : String) : PokemonTypeResults{
        return RetrofitInstance.api.getPokemonTypeList(type)
    }
}