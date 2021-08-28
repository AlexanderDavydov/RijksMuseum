package ru.alexandro.domain.usecase.artobject

import ru.alexandro.domain.exceptions.QueryParamsException
import ru.alexandro.domain.model.ArtObjectDetail
import ru.alexandro.domain.repository.ArtObjectRepository
import ru.alexandro.domain.usecase.base.BaseDeferredUseCase

class RetrieveArtObjectDetailUseCase(
    private val objectRepository: ArtObjectRepository
    ) : BaseDeferredUseCase<String, ArtObjectDetail>() {

    override suspend fun run(params: String?): ArtObjectDetail {
        val objectNumber = params ?: throw QueryParamsException()
        return objectRepository.getArtObjectDetail(objectNumber)
    }
}