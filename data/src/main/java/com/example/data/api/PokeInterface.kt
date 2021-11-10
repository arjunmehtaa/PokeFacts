package com.example.data.api

import com.example.domain.model.Pokemon
import com.example.domain.model.Species
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeInterface {
    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id : Int
    ): Pokemon

    @GET("pokemon-species/{id}")
    suspend fun getSpecies(
        @Path("id") id : Int
    ): Species
}