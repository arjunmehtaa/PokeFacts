package com.arjuj.domain.di

import com.arjuj.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetPokemonUseCase(get()) }
    factory { AddFavoritePokemonUseCase(get()) }
    factory { DeleteFavoritePokemonUseCase(get()) }
    factory { GetFavoritePokemonListUseCase(get()) }
    factory { GetIsPokemonFavoriteUseCase(get()) }
    factory { GetAllPokemonNamesUseCase(get()) }
    factory { GetAllPokemonOfTypeUseCase(get()) }
}