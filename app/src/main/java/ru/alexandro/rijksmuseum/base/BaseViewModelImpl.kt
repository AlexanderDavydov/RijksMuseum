package ru.alexandro.rijksmuseum.base

import androidx.annotation.CallSuper
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject
import ru.alexandro.rijksmuseum.extentions.noReplyMutableSharedFlow
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseViewModelImpl<VS : BaseViewState, E : BaseEvent>(
    val savedState: SavedStateHandle
) : ViewModel(), BaseViewModel<VS, E> {

    private val eventHandler: IEventHandler<E> by lazy { EventHandler(::commonEventHandler) }

    override val error = noReplyMutableSharedFlow<Throwable>()

    abstract override val viewState: StateFlow<VS>

    override val router: Router by inject()

    /**
     * Main channel of view events. By default all events handles in Main dispatcher.
     * Use [longRunning] to switch context to [Dispatchers.IO]
     */
    @ObsoleteCoroutinesApi
    private val actor = viewModelScope.actor<E>(
        start = CoroutineStart.LAZY,
        capacity = Channel.UNLIMITED
    ) {
        consumeEach { eventHandler.handleEvent(it) }
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    override fun sendEvent(event: E): Boolean =
        if (actor.isClosedForSend) {
            Timber.d("Event channel is closed for send.")
            false
        } else {
            actor.trySend(event).isSuccess
        }

    override fun <Type : E> cancelEvent(event: KClass<Type>) {
        eventHandler.cancelEvent(event)
    }

    /**
     * Method to handle events from view.
     */
    protected abstract suspend fun handleEvent(event: E)

    /**
     * Method to handle exceptions which can be thrown while handling events.
     */
    @CallSuper
    protected open fun handleError(event: E, exception: Throwable) {
        when (exception) {
            is CancellationException -> return
            else -> forwardError(exception)
        }
    }

    private fun forwardError(exception: Throwable) {
        error.tryEmit(exception)
    }

    /**
     * Use this utils method to switch context for long operations.
     */
    protected suspend fun <T> longRunning(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            block()
        }

    /**
     * Util method which creates [CoroutineExceptionHandler]
     */
    protected fun handleError(block: (Throwable) -> Unit): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable -> block(throwable) }

    private fun commonEventHandler(event: E): Job =
        viewModelScope.launch(Dispatchers.Default) {
            try {
                handleEvent(event)
            } catch (ex: Throwable) {
                handleError(event, ex)
            }
        }


}