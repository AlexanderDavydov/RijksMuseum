package ru.alexandro.domain.usecase.artobject

import ru.alexandro.domain.exceptions.QueryParamsException
import ru.alexandro.domain.model.ArtObjectListData
import ru.alexandro.domain.repository.ArtObjectRepository
import ru.alexandro.domain.usecase.artobject.RetrieveArtObjectListUseCase.Params
import ru.alexandro.domain.usecase.base.BaseDeferredUseCase

/**
 * Provides a list of art objects base on [Params]
 * Support pagination requests
 */
class RetrieveArtObjectListUseCase(
    private val objectRepository: ArtObjectRepository
) : BaseDeferredUseCase<Params, ArtObjectListData>() {

    override suspend fun run(params: Params?): ArtObjectListData {
        val query = params ?: throw QueryParamsException()

        return objectRepository.getArtObjectList(
            query.pageStart,
            query.pageSize
        )
    }

    data class Params(
        val pageStart: Int,
        val pageSize: Int,
    )
}