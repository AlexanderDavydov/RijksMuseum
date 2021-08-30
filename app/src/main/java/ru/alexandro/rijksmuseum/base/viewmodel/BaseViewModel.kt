package ru.alexandro.rijksmuseum.base.viewmodel

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import ru.alexandro.rijksmuseum.base.event.BaseEvent
import ru.alexandro.rijksmuseum.base.view.BaseViewState
import kotlin.reflect.KClass

/**
 * Common Interface to work with View Model
 */
interface BaseViewModel<VS : BaseViewState, E : BaseEvent> : KoinComponent {

    val viewState: StateFlow<VS>

    val router: Router

    /**
     * Error event. Notify view about errors.
     */
    val error: SharedFlow<Throwable>

    /**
     * Method to send event to view model's event channel.
     *
     * @return true if event was sent, false otherwise
     */
    fun sendEvent(event: E): Boolean

    /**
     * Method to cancel event from event queue.
     */
    fun <Type : E> cancelEvent(event: KClass<Type>)
}