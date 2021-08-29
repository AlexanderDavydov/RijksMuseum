package ru.alexandro.rijksmuseum.presentation.listsections.viewmodel

import androidx.paging.PagingData
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.rijksmuseum.base.event.BaseEvent
import ru.alexandro.rijksmuseum.base.view.BaseViewState
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModel
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel
import ru.alexandro.rijksmuseum.presentation.listsections.adapter.ArtObjectItem
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListEvent
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListViewState

interface ArtObjectSectionListViewModel : BaseViewModel<ArtObjectSectionListViewState, ArtObjectSectionListEvent> {

    sealed class ArtObjectSectionListViewState : BaseViewState {
        object Loading : ArtObjectSectionListViewState()
        data class ArtObjectPage(val data: PagingData<ArtObjectItem>) : ArtObjectSectionListViewState()
    }

    sealed class ArtObjectSectionListEvent : BaseEvent {
        object LoadArtObjectList : ArtObjectSectionListEvent()
        data class ArtObjectClick(val objectNumber: String): ArtObjectSectionListEvent()
        data class ShareArtObject(val webLink: String) : ArtObjectSectionListEvent()
    }
}