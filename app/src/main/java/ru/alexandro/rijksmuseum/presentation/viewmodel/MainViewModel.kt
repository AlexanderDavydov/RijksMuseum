package ru.alexandro.rijksmuseum.presentation.viewmodel

import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModel.MainEvent
import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModel.MainViewState
import ru.alexandro.rijksmuseum.base.BaseEvent
import ru.alexandro.rijksmuseum.base.BaseViewModel
import ru.alexandro.rijksmuseum.base.BaseViewState

interface MainViewModel : BaseViewModel<MainViewState, MainEvent> {

    sealed class MainViewState : BaseViewState {
        object Splash : MainViewState()
        object ListScreen : MainViewState()
    }

    sealed class MainEvent : BaseEvent {
        object ViewReady: MainEvent()
    }
}