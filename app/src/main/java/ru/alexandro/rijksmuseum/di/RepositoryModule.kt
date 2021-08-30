package ru.alexandro.rijksmuseum.di

import org.koin.dsl.module
import ru.alexandro.data.repository.ArtObjectRepositoryImpl
import ru.alexandro.domain.repository.ArtObjectRepository

internal val repositoryModule = module {
    single<ArtObjectRepository> { ArtObjectRepositoryImpl(get()) }
}
