package ru.alexandro.rijksmuseum.extentions

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * MutableSharedFlow with 0 reply value
 *  consists of DROP_OLDEST strategy and 5 element buffer
 */
fun <T> noReplyMutableSharedFlow() =
    MutableSharedFlow<T>(extraBufferCapacity = 5, onBufferOverflow = BufferOverflow.DROP_OLDEST)


/**
 * Set/get value to the slow
 *
 * get will returns first value from replayCache
 * set will call tryEmit to the flow
 *  be sure to initialise your SharedFlow correctly
 */
inline var <reified T> MutableSharedFlow<T>.value
    get() = replayCache.firstOrNull()
    set(value) {
        tryEmit(value ?: throw IllegalStateException("try to emit null"))
    }