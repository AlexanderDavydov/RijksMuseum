package ru.alexandro.domain.repository

import ru.alexandro.domain.model.ArtObject

interface ArtObjectRepository {

    suspend fun getArtObjectList(pageStart: Int, pageSize: Int): List<ArtObject>
}