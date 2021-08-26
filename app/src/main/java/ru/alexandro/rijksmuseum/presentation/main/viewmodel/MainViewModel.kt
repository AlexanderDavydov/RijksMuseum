package ru.alexandro.rijksmuseum.presentation.main.viewmodel

import ru.alexandro.rijksmuseum.presentation.main.viewmodel.MainViewModel.MainEvent
import ru.alexandro.rijksmuseum.presentation.main.viewmodel.MainViewModel.MainViewState
import ru.alexandro.rijksmuseum.base.event.BaseEvent
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModel
import ru.alexandro.rijksmuseum.base.view.BaseViewState

interface MainViewModel : BaseViewModel<MainViewState, MainEvent> {

    sealed class MainViewState : BaseViewState {
        object Splash : MainViewState()
        object ListScreen : MainViewState()
    }

    sealed class MainEvent : BaseEvent {
        object ViewReady: MainEvent()
    }
}