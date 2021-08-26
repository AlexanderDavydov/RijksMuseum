package ru.alexandro.rijksmuseum.presentation.list.viewmodel

import androidx.paging.PagingData
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.rijksmuseum.base.event.BaseEvent
import ru.alexandro.rijksmuseum.base.view.BaseViewState
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModel
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListEvent
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListViewState

interface ArtObjectListViewModel : BaseViewModel<ArtObjectListViewState, ArtObjectListEvent> {

    sealed class ArtObjectListViewState : BaseViewState {
        object Loading : ArtObjectListViewState()
        data class ArtObjectPage(val data: PagingData<ArtObject>) : ArtObjectListViewState()
    }

    sealed class ArtObjectListEvent : BaseEvent {
        object LoadArtObjectList : ArtObjectListEvent()
        data class ArtObjectClick(val id: String): ArtObjectListEvent()
        data class ShareArtObject(val webLink: String) : ArtObjectListEvent()
    }
}