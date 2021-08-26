package ru.alexandro.domain.usecase.base

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Base interface for UseCase.
 * [Source] type of result
 * [Params] type of parameters
 */
interface UseCase<Source, Params> {

    /**
     * Saved [Deferred]/[Job]/[Channel] which can be used to restart execution.
     */
    var source: Source?

    /**
     * Base coroutine context.
     */
    val coroutineContext: CoroutineContext

    /**
     * Execute this UseCase.
     * @param params parameters.
     * @return [Deferred]
     */
    fun executeAsync(coroutineScope: CoroutineScope, params: Params? = null): Source

}
