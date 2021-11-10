package com.example.domain.usecases

import com.example.domain.model.FlavorTextEntry
import com.example.domain.model.Genera
import com.example.domain.model.Pokemon
import com.example.domain.repository.RepositoryInterface

class GetPokemonUseCase(private val repository: RepositoryInterface) {

    private var list: MutableList<Pokemon> = mutableListOf()

    suspend fun getPokemon(numberOfPokemons: Int, offset: Int): MutableList<Pokemon> {
        for (i in (offset + 1)..(numberOfPokemons + offset)) {
            val pokemon = repository.getPokemon(i)
            val species = repository.getSpecies(i)
            pokemon.genera = getPokemonGenera(species.genera)
            pokemon.description = getPokemonDescription(species.flavor_text_entries)
            pokemon.capture_rate = species.capture_rate
            if(!list.contains(pokemon)) list.add(pokemon)
        }
        return list
    }

    private fun getPokemonGenera(generaList: List<Genera>?): String {
        var index = 0
        while (generaList?.get(index)?.language?.name != "en") {
            index++
        }
        return generaList[index].genus
    }

    private fun getPokemonDescription(flavorTextList: List<FlavorTextEntry>): String {
        var index = flavorTextList.size - 1
        while (flavorTextList[index].language.name != "en") {
            index--
        }
        var flavorText = flavorTextList[index].flavor_text
        flavorText = flavorText.replace("POKéMON", "Pokémon")
        flavorText = flavorText.replace("\n", " ")
        return flavorText
    }
}