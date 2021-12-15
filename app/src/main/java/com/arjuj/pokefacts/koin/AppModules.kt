package com.arjuj.pokefacts.koin

import com.arjuj.pokefacts.ui.favorites.FavoritesViewModel
import com.arjuj.pokefacts.ui.home.HomeViewModel
import com.arjuj.pokefacts.ui.info.InfoViewModel
import com.arjuj.pokefacts.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get(), get(), get()) }
    viewModel { InfoViewModel(get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get(), get(), get()) }
}