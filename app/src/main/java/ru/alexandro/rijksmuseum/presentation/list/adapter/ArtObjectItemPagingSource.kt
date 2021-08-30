package ru.alexandro.rijksmuseum.presentation.list.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.domain.model.ArtObjectListData
import ru.alexandro.rijksmuseum.presentation.list.adapter.ArtObjectItem.ArtObjectItemData
import ru.alexandro.rijksmuseum.presentation.list.adapter.ArtObjectItem.ArtObjectItemHeader

class ArtObjectItemPagingSource(
    private val pageLoader: suspend (Int, Int) -> ArtObjectListData,
    private val errorHandler: (e: Throwable) -> Unit,
) : PagingSource<Int, ArtObjectItem>() {

    private val sections = mutableSetOf<Char>()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObjectItem> {
        return try {
            val offset = params.key ?: 0
            val loadSize = params.loadSize
            val result = pageLoader(offset, loadSize)
            val itemsCountLeft = result.count - offset

            val items = buildItems(result.artObjects)

            LoadResult.Page(
                data = items,
                prevKey = if (offset == 0) null else loadSize,
                nextKey = if (itemsCountLeft <= 0) null else offset + result.artObjects.size
            )
        } catch (e: Throwable) {
            errorHandler(e)
            LoadResult.Error(throwable = e)
        }
    }

    private fun buildItems(artObjects: List<ArtObject>): List<ArtObjectItem> {
        val newSections = artObjects.map { it.principalOrFirstMaker }
            .map { it[0] }
            .toSet()

        val items = mutableListOf<ArtObjectItem>()

        if (sections.containsAll(newSections)) {
            items.addAll(
                artObjects.map { ArtObjectItemData(it) }
            )
        } else {
            newSections.forEach { letter ->
                if (sections.contains(letter).not()) {
                    items.add(
                        ArtObjectItemHeader(letter.toString())
                    )
                    sections.add(letter)
                }
                artObjects.filter { it.principalOrFirstMaker.startsWith(letter) }
                    .map { ArtObjectItemData(it) }
                    .also { items.addAll(it) }
            }
        }

        return items
    }

    override fun getRefreshKey(state: PagingState<Int, ArtObjectItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        val pageSize = state.config.pageSize
        return page.prevKey?.plus(pageSize) ?: page.prevKey?.minus(pageSize)
    }
}