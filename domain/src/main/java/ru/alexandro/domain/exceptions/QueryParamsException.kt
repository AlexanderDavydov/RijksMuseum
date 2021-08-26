package ru.alexandro.domain.exceptions

/**
 * Exception that notifies issues with the use case parameter
 */
class QueryParamsException(
    message: String = "Invalid Params"
) : Throwable(message)