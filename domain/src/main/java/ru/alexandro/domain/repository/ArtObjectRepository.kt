package ru.alexandro.domain.repository

import ru.alexandro.domain.model.ArtObjectDetail
import ru.alexandro.domain.model.ArtObjectListData

interface ArtObjectRepository {

    suspend fun getArtObjectList(pageStart: Int, pageSize: Int): ArtObjectListData

    suspend fun getArtObjectDetail(objectNumber: String): ArtObjectDetail
}