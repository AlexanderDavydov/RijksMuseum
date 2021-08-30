package ru.alexandro.rijksmuseum.base.event

import kotlinx.coroutines.Job
import java.util.*
import kotlin.reflect.KClass

/**
 * Simple Event Handler Implementation
 */
class EventHandlerImpl<TEvent : BaseEvent>(
    private val commonHandler: (TEvent) -> Job
) : EventHandler<TEvent> {

    private val eventQueue: TreeMap<String, Job> by lazy { TreeMap<String, Job>() }

    override suspend fun handleEvent(event: TEvent): Unit = with(event) {
        val key = this::class.java.name
        handleCommonEvent(event, key)
    }

    override fun <TType : TEvent> cancelEvent(event: KClass<TType>) {
        eventQueue[event.java.name]?.cancel()
    }

    private fun handleCommonEvent(event: TEvent, id: String) {
        commonHandler(event).also { job ->
            eventQueue[id] = job
            job.invokeOnCompletion { eventQueue.remove(id) }
        }
    }
}