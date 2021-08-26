package ru.alexandro.domain.repository

import ru.alexandro.domain.model.ArtObject
import ru.alexandro.domain.model.ArtObjectListData

interface ArtObjectRepository {

    suspend fun getArtObjectList(pageStart: Int, pageSize: Int): ArtObjectListData
}