package ru.alexandro.data.mapping.response

import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectDetailResponce(
    val artObject: RawArtObjectDetail,
) {

    @Serializable
    data class RawArtObjectDetail(
        val id: String,
        val priref: String,
        val objectNumber: String,
        val title: String,
        val webImage: WebImage,
        val description: String?,
        val principalMakers: List<PrincipalMaker>
    )

    @Serializable
    data class WebImage(
        val guid: String,
        val offsetPercentageX: Int,
        val offsetPercentageY: Int,
        val width: Int,
        val height: Int,
        val url: String,
    )

    @Serializable
    data class PrincipalMaker(
        val name: String,
    )
}