package ru.alexandro.data.repository

import ru.alexandro.data.api.ArtObjectApi
import ru.alexandro.data.mapping.toArtObject
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.domain.repository.ArtObjectRepository

class ArtObjectRepositoryImpl(
    private val artObjectApi: ArtObjectApi
): ArtObjectRepository {

    /**
     * The language of the page
     */
    val culture = "en"

    override suspend fun getArtObjectList(pageStart: Int, pageSize: Int): List<ArtObject> {
        return artObjectApi.getArtObjectList(culture, pageStart, pageSize)
            .await()
            .artObjects
            .map { it.toArtObject() }
    }
}