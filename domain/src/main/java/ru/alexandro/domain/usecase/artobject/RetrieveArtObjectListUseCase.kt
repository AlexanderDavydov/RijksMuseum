package ru.alexandro.domain.usecase.artobject

import ru.alexandro.domain.exceptions.QueryParamsException
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.domain.repository.ArtObjectRepository
import ru.alexandro.domain.usecase.base.BaseDeferredUseCase

class RetrieveArtObjectListUseCase(
    private val objectRepository: ArtObjectRepository
) : BaseDeferredUseCase<RetrieveArtObjectListUseCase.Params, List<ArtObject>>() {


    override suspend fun run(params: Params?): List<ArtObject> {
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