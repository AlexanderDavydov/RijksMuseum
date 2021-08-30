package ru.alexandro.rijksmuseum.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.alexandro.data.api.ArtObjectApi

internal val apiModule = module {
    factory { get<Retrofit>().create(ArtObjectApi::class.java) }
}
