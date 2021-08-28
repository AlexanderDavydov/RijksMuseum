package ru.alexandro.rijksmuseum.presentation.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexandro.domain.usecase.artobject.RetrieveArtObjectDetailUseCase
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModelImpl
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel.ArtDetailEvent
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel.ArtDetailEvent.LoadArtDetail
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel.ArtDetailViewState
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel.ArtDetailViewState.Loading
import ru.alexandro.rijksmuseum.router.Fragments.ArtObjectDetail.Companion.ARG_ART_OBJECT_NUMBER
import ru.alexandro.rijksmuseum.router.ShareAction

class ArtObjectDetailViewModelImpl(
    savedState: SavedStateHandle,
    private val retrieveArtObjectDetailUseCase: RetrieveArtObjectDetailUseCase
) : BaseViewModelImpl<ArtDetailViewState, ArtDetailEvent>(savedState),
    ArtObjectDetailViewModel {

    private val objectNumber by lazy { savedState.get<String>(ARG_ART_OBJECT_NUMBER) }

    override val viewState = MutableStateFlow<ArtDetailViewState>(Loading)

    init {
        sendEvent(LoadArtDetail)
    }

    override suspend fun handleEvent(event: ArtDetailEvent) {
        when (event) {
            is LoadArtDetail -> longRunning {
                val objNum = objectNumber ?: throw IllegalArgumentException(
                    "Object Number must not be null"
                )
                handleLoadArtDetailViewModel(objNum)
            }
            is ArtDetailEvent.ShareArtObject -> handleShareArtObject(event.webLink)
        }
    }

    private suspend fun handleLoadArtDetailViewModel(objectNumber: String) {
        val detail =
            retrieveArtObjectDetailUseCase.executeAsync(viewModelScope, objectNumber).await()

        viewState.tryEmit(ArtDetailViewState.ArtObjectData(detail))
    }

    private fun handleShareArtObject(webLink: String) {
        router.navigateTo(ShareAction(webLink))
    }
}
