package com.arjuj.pokefacts

import android.app.Application
import com.arjuj.data.di.dataModule
import com.arjuj.domain.di.domainModule
import com.arjuj.pokefacts.koin.appModule
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