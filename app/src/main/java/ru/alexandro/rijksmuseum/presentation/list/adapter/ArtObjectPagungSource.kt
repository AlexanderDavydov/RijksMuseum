package ru.alexandro.rijksmuseum.presentation.list.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.domain.model.ArtObjectListData

class ArtObjectPagungSource(
    private val pageLoader: suspend (Int, Int) -> ArtObjectListData
) : PagingSource<Int, ArtObject>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
        return try {
            val offset = params.key ?: 0
            val loadSize = params.loadSize
            val result = pageLoader(offset, loadSize)
            val itemsCountLeft = result.count - offset

            LoadResult.Page(
                data = result.artObjects,
                prevKey = if (offset == 0) null else loadSize,
                nextKey = if (itemsCountLeft <= 0) null else offset + result.artObjects.size
            )
        } catch (e: Throwable) {
            LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        val pageSize = state.config.pageSize
        return page.prevKey?.plus(pageSize) ?: page.prevKey?.minus(pageSize)
    }
}