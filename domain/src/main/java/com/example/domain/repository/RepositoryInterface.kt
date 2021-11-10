package com.example.domain.repository

import com.example.domain.model.Pokemon
import com.example.domain.model.Species

interface RepositoryInterface {
    suspend fun getPokemon(id : Int) : Pokemon
    suspend fun getSpecies(id : Int) : Species
}