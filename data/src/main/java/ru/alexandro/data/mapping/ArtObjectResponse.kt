package ru.alexandro.data.mapping

import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectResponse(
    val count: Int,
    val artObjects: List<RawArtObject>
) {

    @Serializable
    data class RawArtObject(
        val links: Links,
        val id: String,
        val objectNumber: String,
        val principalOrFirstMaker: String,
        val title: String,
        val longTitle: String,
        val webImage: WebImage,
        val productionPlaces: List<String>
    ) {
        @Serializable
        data class Links(
            val self: String,
            val web: String
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
    }
}