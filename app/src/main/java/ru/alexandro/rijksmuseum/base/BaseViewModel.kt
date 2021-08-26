package ru.alexandro.rijksmuseum.base

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import kotlin.reflect.KClass

interface BaseViewModel<VS : BaseViewState, E : BaseEvent> : KoinComponent {

    val viewState: StateFlow<VS>

    val router: Router

    /**
     * Error live event. Notify view about errors. Based on SharedFlow.
     */
    val error: SharedFlow<Throwable>

    /**
     * Method to send event to viewmodel's event channel.
     *
     * @return true if event was sent and false if wasn't.
     */
    fun sendEvent(event: E): Boolean

    /**
     * Method to cancel event from event queue.
     */
    fun <Type : E> cancelEvent(event: KClass<Type>)

}