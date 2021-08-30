package ru.alexandro.rijksmuseum.presentation.list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexandro.domain.model.ArtObjectListData
import ru.alexandro.domain.usecase.artobject.RetrieveArtObjectListUseCase
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModelImpl
import ru.alexandro.rijksmuseum.extentions.observe
import ru.alexandro.rijksmuseum.presentation.list.adapter.ArtObjectItemPagingSource
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListEvent
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListEvent.ArtObjectClick
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListEvent.LoadArtObjectList
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListViewState
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListViewState.ArtObjectPage
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListViewState.Loading
import ru.alexandro.rijksmuseum.router.Fragments
import ru.alexandro.rijksmuseum.router.ShareAction

class ArtObjectSectionListViewModelImpl(
    savedState: SavedStateHandle,
    private val retrieveArtObjectListUseCase: RetrieveArtObjectListUseCase
) : BaseViewModelImpl<ArtObjectSectionListViewState, ArtObjectSectionListEvent>(savedState),
    ArtObjectSectionListViewModel {

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }

    override val viewState = MutableStateFlow<ArtObjectSectionListViewState>(Loading)

    val pager = Pager(
        PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            initialLoadSize = DEFAULT_PAGE_SIZE,
        )
    ) {
        ArtObjectItemPagingSource(
            pageLoader = ::loadArtObjectList,
            errorHandler = { handleError(LoadArtObjectList, it) }
        )
    }

    init {
        sendEvent(LoadArtObjectList)
    }

    override suspend fun handleEvent(event: ArtObjectSectionListEvent) {
        when (event) {
            is LoadArtObjectList -> longRunning { handleLoadArtObjectList() }
            is ArtObjectClick -> handleArtObjectClick(event.objectNumber)
            is ArtObjectSectionListEvent.ShareArtObject -> handleShareArtObject(event.webLink)
        }
    }

    private suspend fun handleLoadArtObjectList() {
        pager.flow
            .cachedIn(viewModelScope)
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

    private fun handleArtObjectClick(objectNumber: String) {
        router.navigateTo(Fragments.ArtObjectDetail(objectNumber))
    }
}
