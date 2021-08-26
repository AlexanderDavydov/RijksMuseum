package ru.alexandro.rijksmuseum.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

internal val routerModule = module {

    single { cicerone() }
    single { getNavigatorHolder(get()) }
    single { getRouter(get()) }


}

private fun cicerone() = Cicerone.create()

private fun getRouter(cicerone: Cicerone<*>): Router = cicerone.router as Router

private fun getNavigatorHolder(cicerone: Cicerone<*>) = cicerone.getNavigatorHolder()


