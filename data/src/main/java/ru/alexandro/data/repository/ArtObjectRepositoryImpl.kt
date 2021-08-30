package ru.alexandro.data.repository

import ru.alexandro.data.api.ArtObjectApi
import ru.alexandro.data.mapping.toArtObjectDetail
import ru.alexandro.data.mapping.toArtObjectListData
import ru.alexandro.domain.model.ArtObjectDetail
import ru.alexandro.domain.model.ArtObjectListData
import ru.alexandro.domain.repository.ArtObjectRepository

class ArtObjectRepositoryImpl(
    private val artObjectApi: ArtObjectApi
) : ArtObjectRepository {

    /**
     * The language of the page
     */
    val culture = "en"

    override suspend fun getArtObjectList(pageStart: Int, pageSize: Int): ArtObjectListData {
        return artObjectApi.getArtObjectList(culture, pageStart, pageSize)
            .await()
            .toArtObjectListData()
    }

    override suspend fun getArtObjectDetail(objectNumber: String): ArtObjectDetail {
        return artObjectApi.getArtObjectDetail(culture, objectNumber)
            .await()
            .artObject
            .toArtObjectDetail()
    }
}