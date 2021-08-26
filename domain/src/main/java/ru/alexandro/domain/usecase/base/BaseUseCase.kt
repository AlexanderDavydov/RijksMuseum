package ru.alexandro.domain.usecase.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Base implementation of UseCase.
 */
abstract class BaseUseCase<Source : Job, Params, Result> : UseCase<Source, Params> {

    override var source: Source? = null

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    abstract override fun executeAsync(coroutineScope: CoroutineScope, params: Params?): Source

    /**
     * Abstract method which will be run while execution.
     */
    protected abstract suspend fun run(params: Params?): Result

}