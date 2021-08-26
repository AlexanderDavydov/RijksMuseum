package ru.alexandro.rijksmuseum.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModelImpl
import ru.alexandro.rijksmuseum.presentation.main.viewmodel.MainViewModelImpl

internal val viewModelModules = module {
    viewModel { MainViewModelImpl(get()) }
    viewModel { ArtObjectListViewModelImpl(get(), get()) }
}