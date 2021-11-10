package com.example.pokefacts.koin

import com.example.pokefacts.ui.favorites.FavoritesViewModel
import com.example.pokefacts.ui.home.HomeViewModel
import com.example.pokefacts.ui.info.InfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get())}
    viewModel { FavoritesViewModel(get(), get(), get(), get()) }
    viewModel { InfoViewModel(get(), get(), get()) }
}