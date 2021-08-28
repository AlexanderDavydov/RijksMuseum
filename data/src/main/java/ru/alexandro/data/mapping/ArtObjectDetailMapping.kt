package ru.alexandro.data.mapping

import ru.alexandro.data.mapping.response.ArtObjectDetailResponce.RawArtObjectDetail
import ru.alexandro.domain.model.ArtObjectDetail

fun RawArtObjectDetail.toArtObjectDetail() = ArtObjectDetail(
    title = title,
    description = description ?: "",
    artists = principalMakers.map { it.name },
    imageUrl = webImage.url
)