package ru.alexandro.data.mapping

import ru.alexandro.data.mapping.response.ArtObjectResponse
import ru.alexandro.data.mapping.response.ArtObjectResponse.RawArtObject
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.domain.model.ArtObjectListData


fun ArtObjectResponse.toArtObjectListData(): ArtObjectListData = ArtObjectListData(
    count = count,
    artObjects = artObjects.map { it.toArtObject() }
)

fun RawArtObject.toArtObject() = ArtObject(
    id = id,
    title = title,
    longTitle = longTitle,
    imageUrl = this.webImage.url,
    webLink = links.web
)