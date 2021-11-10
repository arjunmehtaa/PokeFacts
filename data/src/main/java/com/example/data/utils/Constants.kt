package com.example.data.utils

class Constants {
    companion object{
        const val BASE_URL = "https://pokeapi.co/api/v2/"
        const val POKEMONS_LOAD_LIMIT = 20
        const val TOTAL_POKEMONS = 898
        const val LAST_GROUP_LOAD_LIMIT = TOTAL_POKEMONS % POKEMONS_LOAD_LIMIT
        const val POKEMONS_BEFORE_LAST_GROUP = TOTAL_POKEMONS - LAST_GROUP_LOAD_LIMIT
    }
}