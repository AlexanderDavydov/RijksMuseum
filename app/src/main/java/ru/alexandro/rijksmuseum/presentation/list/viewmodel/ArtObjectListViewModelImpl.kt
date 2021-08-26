package ru.alexandro.rijksmuseum.presentation.list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.alexandro.domain.model.ArtObjectListData
import ru.alexandro.domain.usecase.artobject.RetrieveArtObjectListUseCase
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModelImpl
import ru.alexandro.rijksmuseum.extentions.observe
import ru.alexandro.rijksmuseum.presentation.list.adapter.ArtObjectPagungSource
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListEvent
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListEvent.*
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListViewState
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListViewState.ArtObjectPage
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListViewState.Loading
import ru.alexandro.rijksmuseum.router.ShareAction

class ArtObjectListViewModelImpl(
    savedState: SavedStateHandle,
    private val retrieveArtObjectListUseCase: RetrieveArtObjectListUseCase
) : BaseViewModelImpl<ArtObjectListViewState, ArtObjectListEvent>(savedState),
    ArtObjectListViewModel {

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }

    override val viewState = MutableStateFlow<ArtObjectListViewState>(Loading)

    val pager = Pager(
        PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            initialLoadSize = DEFAULT_PAGE_SIZE,
        )
    ) { ArtObjectPagungSource(::loadArtObjectList) }

    init {
        sendEvent(LoadArtObjectList)
    }

    override suspend fun handleEvent(event: ArtObjectListEvent) {
        when (event) {
            is LoadArtObjectList -> longRunning { handleLoadArtObjectList() }
            is ShareArtObject -> handleShareArtObject(event.webLink)
            is ArtObjectClick -> handleArtObjectClick(event.id)
        }
    }

    private suspend fun handleLoadArtObjectList() {
        pager.flow
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
            .observe(viewModelScope) { viewState.emit(ArtObjectPage(it)) }
    }

    private suspend fun loadArtObjectList(page: Int, pageSize: Int): ArtObjectListData {
        return retrieveArtObjectListUseCase.executeAsync(
            viewModelScope,
            RetrieveArtObjectListUseCase.Params(page, pageSize)
        ).await()
    }

    private fun handleShareArtObject(webLink: String) {
        router.navigateTo(ShareAction(webLink))
    }

    private fun handleArtObjectClick(id: String) {
        // router.navigateTo(ArtObjectDetail(id))
    }
}
