package com.example.pokefacts

import android.app.Application
import com.example.data.di.dataModule
import com.example.pokefacts.koin.appModule
import com.example.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokeFactsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokeFactsApp)
            modules(listOf(appModule,dataModule,domainModule))
        }
    }
}