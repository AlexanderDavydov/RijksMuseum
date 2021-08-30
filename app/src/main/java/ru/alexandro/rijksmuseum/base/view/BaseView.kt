package ru.alexandro.rijksmuseum.base.view

import androidx.viewbinding.ViewBinding
import ru.alexandro.rijksmuseum.base.event.BaseEvent
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModel

/**
 * Common interface to work with view.
 * Provide base field to work with viewmodel, view layout and handling view state
 */
interface BaseView<E : BaseEvent, VM : BaseViewModel<VS, E>, VB : ViewBinding, VS : BaseViewState> {

    val viewModel: VM

    val binding: VB

    fun handleViewState(viewState: VS)
}