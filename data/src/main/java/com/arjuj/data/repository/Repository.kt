package com.arjuj.data.repository

import com.arjuj.data.api.RetrofitInstance
import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.model.PokemonResults
import com.arjuj.domain.model.PokemonTypeResults
import com.arjuj.domain.model.Species
import com.arjuj.domain.repository.RepositoryInterface

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