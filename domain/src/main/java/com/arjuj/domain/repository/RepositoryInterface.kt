package com.arjuj.domain.repository

import com.arjuj.domain.model.Pokemon
import com.arjuj.domain.model.PokemonResults
import com.arjuj.domain.model.PokemonTypeResults
import com.arjuj.domain.model.Species

interface RepositoryInterface {
    suspend fun getPokemon(id: Int): Pokemon
    suspend fun getSpecies(id: Int): Species
    suspend fun getPokemonList(limit: Int): PokemonResults
    suspend fun getPokemonTypeList(type : String) : PokemonTypeResults
}