package ru.alexandro.rijksmuseum.presentation.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModelImpl
import ru.alexandro.rijksmuseum.presentation.main.viewmodel.MainViewModel.MainEvent
import ru.alexandro.rijksmuseum.presentation.main.viewmodel.MainViewModel.MainEvent.ViewReady
import ru.alexandro.rijksmuseum.presentation.main.viewmodel.MainViewModel.MainViewState
import ru.alexandro.rijksmuseum.router.Fragments

class MainViewModelImpl(
    savedState: SavedStateHandle
) : BaseViewModelImpl<MainViewState, MainEvent>(savedState), MainViewModel {

    override val viewState = MutableStateFlow<MainViewState>(MainViewState.Splash)

    override suspend fun handleEvent(event: MainEvent) {
         when (event) {
             is ViewReady -> handleShowScreen()
         }
    }

    private suspend fun handleShowScreen() {
        when (viewState.value) {
            is MainViewState.Splash -> {
                handleSplashScreen()
            }
        }
    }

    private suspend fun handleSplashScreen() {
        // do any additional async work before main screen initialisation

        // set main fragment before splash closed
        router.newRootScreen(Fragments.ArtObjectSectionList)

        // for smooth transition waiting for 2 seconds
        delay(2000)
        viewState.value = MainViewState.ListScreen
    }
}