package com.example.data.api

import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonResults
import com.example.domain.model.PokemonTypeResults
import com.example.domain.model.Species
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeInterface {
    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int
    ): Pokemon

    @GET("pokemon-species/{id}")
    suspend fun getSpecies(
        @Path("id") id: Int
    ): Species

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int
    ): PokemonResults

    @GET("type/{type}")
    suspend fun getPokemonTypeList(
        @Path("type") type: String
    ): PokemonTypeResults
}