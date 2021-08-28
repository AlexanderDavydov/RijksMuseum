package ru.alexandro.rijksmuseum.presentation.detail.viewmodel

import ru.alexandro.domain.model.ArtObjectDetail
import ru.alexandro.rijksmuseum.base.event.BaseEvent
import ru.alexandro.rijksmuseum.base.view.BaseViewState
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModel
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel.ArtDetailEvent
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel.ArtDetailViewState

interface ArtObjectDetailViewModel : BaseViewModel<ArtDetailViewState, ArtDetailEvent> {

    sealed class ArtDetailViewState : BaseViewState {
        object Loading : ArtDetailViewState()
        data class ArtObjectData(val data: ArtObjectDetail) : ArtDetailViewState()
    }

    sealed class ArtDetailEvent : BaseEvent {
        object LoadArtDetail : ArtDetailEvent()
        data class ShareArtObject(val webLink: String) : ArtDetailEvent()
    }
}