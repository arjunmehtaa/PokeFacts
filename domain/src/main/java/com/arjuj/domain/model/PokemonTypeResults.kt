package com.arjuj.domain.model

import com.google.gson.annotations.SerializedName

data class PokemonTypeResults(@SerializedName("pokemon") val results: List<TypePokemon>)