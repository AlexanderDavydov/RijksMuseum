package ru.alexandro.rijksmuseum.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModelImpl
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectSectionListViewModelImpl
import ru.alexandro.rijksmuseum.presentation.main.viewmodel.MainViewModelImpl

internal val viewModelModules = module {
    viewModel { MainViewModelImpl(get()) }
    viewModel { ArtObjectDetailViewModelImpl(get(), get()) }
    viewModel { ArtObjectSectionListViewModelImpl(get(), get()) }
}