package com.arjuj.domain.di

import com.arjuj.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetPokemonUseCase(get(), get(), get()) }
    factory { AddFavoritePokemonUseCase(get()) }
    factory { RemoveFavoritePokemonUseCase(get()) }
    factory { AddPokemonUseCase(get()) }
    factory { GetSinglePokemonUseCase(get()) }
    factory { GetFavoritePokemonListUseCase(get()) }
    factory { GetIsPokemonFavoriteUseCase(get()) }
    factory { GetIsPokemonSavedUseCase(get()) }
    factory { GetAllPokemonNamesUseCase(get()) }
    factory { GetAllPokemonOfTypeUseCase(get()) }
}