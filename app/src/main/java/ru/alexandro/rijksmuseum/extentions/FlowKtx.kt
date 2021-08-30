package ru.alexandro.rijksmuseum.extentions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


fun <T> Flow<T>.observe(
    scope: CoroutineScope,
    action: suspend (T) -> Unit
) = onEach { action(it) }.launchIn(scope)