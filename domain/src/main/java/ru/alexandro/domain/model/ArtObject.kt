package ru.alexandro.domain.model

data class ArtObject(
    val id: String,
    val objectNumber: String,
    val title: String,
    val longTitle: String,
    val imageUrl: String,
    val webLink: String,
    val principalOrFirstMaker: String
)
