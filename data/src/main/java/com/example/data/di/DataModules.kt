package com.example.data.di

import com.example.data.repository.Repository
import com.example.data.room.RoomPokemonDatabase
import com.example.data.room.RoomPokemonRepository
import com.example.domain.repository.RepositoryInterface
import com.example.domain.repository.RoomRepositoryInterface
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    factory<RepositoryInterface> {  Repository() }
    factory<RoomRepositoryInterface> {  RoomPokemonRepository(RoomPokemonDatabase.getDatabase(androidApplication()).roomPokemonDao()) }
}