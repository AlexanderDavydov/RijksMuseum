package ru.alexandro.data.mapping

import ru.alexandro.domain.model.ArtObject


fun ArtObjectResponse.RawArtObject.toArtObject() = ArtObject(
    id = id,
    title = title,
    longTitle = longTitle,
    imageUrl = this.webImage.url
)