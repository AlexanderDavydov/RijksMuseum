package ru.alexandro.rijksmuseum.base

import androidx.viewbinding.ViewBinding

interface BaseView<E : BaseEvent, VM : BaseViewModel<VS, E>, VB : ViewBinding, VS : BaseViewState> {

    val viewModel: VM

    val binding: VB

    fun handleViewState(viewState: VS)
}