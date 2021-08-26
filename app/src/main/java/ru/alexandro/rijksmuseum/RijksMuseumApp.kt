package ru.alexandro.rijksmuseum

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.alexandro.rijksmuseum.di.rijksMuseumKoinModules
import timber.log.Timber

class RijksMuseumApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RijksMuseumApp)
            modules(rijksMuseumKoinModules)

        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}