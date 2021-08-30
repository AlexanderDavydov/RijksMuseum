package ru.alexandro.domain.usecase.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

/**
 * Base implementation for use case use Deferred as a source
 */
abstract class BaseDeferredUseCase<Params, Result> :
    BaseUseCase<Deferred<Result>, Params, Result>() {

    /**
     * If true previous execution will be cancelled.
     */
    protected open val cancelPrevious: Boolean = true

    override fun executeAsync(coroutineScope: CoroutineScope, params: Params?): Deferred<Result> {
        if (cancelPrevious) source?.cancel()
        return coroutineScope.async(coroutineContext) {
            try {
                run(params)
            } catch (th: Throwable) {
                handleError(th, params)
            }
        }.also { source = it }
    }

    /**
     * If you override this method, you must throw an exception from it.
     */
    protected open suspend fun handleError(th: Throwable, params: Params?): Nothing {
        throw th
    }
}