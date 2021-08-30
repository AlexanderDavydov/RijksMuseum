package ru.alexandro.data.mapping

import ru.alexandro.data.mapping.response.ArtObjectDetailResponse.RawArtObjectDetail
import ru.alexandro.domain.model.ArtObjectDetail

/**
 * Mapping raw Detail data to domain model
 */
fun RawArtObjectDetail.toArtObjectDetail() = ArtObjectDetail(
    title = title,
    description = description ?: "",
    artists = principalMakers.map { it.name },
    imageUrl = webImage.url
)