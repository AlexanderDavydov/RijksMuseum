package ru.alexandro.rijksmuseum.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModelImpl

internal val viewModelModules = module {
    viewModel { MainViewModelImpl(get()) }
//    viewModel { ArtObjectDetailViewModelImpl(get(), get()) }
}