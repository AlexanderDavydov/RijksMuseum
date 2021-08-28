package ru.alexandro.domain.model

data class ArtObjectDetail(
    val title: String,
    val description: String,
    val artists: List<String>,
    val imageUrl: String,
)