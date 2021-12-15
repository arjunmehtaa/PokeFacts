package com.arjuj.data.di

import com.arjuj.data.repository.Repository
import com.arjuj.data.room.RoomPokemonDatabase
import com.arjuj.data.room.RoomPokemonRepository
import com.arjuj.domain.repository.RepositoryInterface
import com.arjuj.domain.repository.RoomRepositoryInterface
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    factory<RepositoryInterface> {  Repository() }
    factory<RoomRepositoryInterface> {  RoomPokemonRepository(RoomPokemonDatabase.getDatabase(androidApplication()).roomPokemonDao()) }
}