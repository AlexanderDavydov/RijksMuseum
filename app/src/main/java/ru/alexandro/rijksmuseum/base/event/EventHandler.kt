package ru.alexandro.rijksmuseum.base.event

import kotlin.reflect.KClass

/**
 * The interface contains basic methods for handling events
 */
interface EventHandler<TEvent : BaseEvent> {

    suspend fun handleEvent(event: TEvent)

    fun <TType : TEvent> cancelEvent(event: KClass<TType>)
}