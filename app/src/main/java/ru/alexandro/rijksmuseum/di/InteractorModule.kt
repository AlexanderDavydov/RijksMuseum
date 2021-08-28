package ru.alexandro.rijksmuseum.di

import org.koin.dsl.module
import ru.alexandro.domain.usecase.artobject.RetrieveArtObjectDetailUseCase
import ru.alexandro.domain.usecase.artobject.RetrieveArtObjectListUseCase

internal val interactorModule = module {
    factory { RetrieveArtObjectListUseCase(get()) }
    factory { RetrieveArtObjectDetailUseCase(get()) }
}