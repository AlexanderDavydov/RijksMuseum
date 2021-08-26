package ru.alexandro.rijksmuseum.extentions

inline fun <T> tryOrNull(block: () -> T): T? =
    try {
        block()
    } catch (ex: Throwable) {
        null
    }