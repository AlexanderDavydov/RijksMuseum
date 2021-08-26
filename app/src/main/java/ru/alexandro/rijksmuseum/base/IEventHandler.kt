package ru.alexandro.rijksmuseum.base

import kotlin.reflect.KClass

interface IEventHandler<TEvent : Any> {

    suspend fun handleEvent(event: TEvent)

    fun <TType : TEvent> cancelEvent(event: KClass<TType>)

}