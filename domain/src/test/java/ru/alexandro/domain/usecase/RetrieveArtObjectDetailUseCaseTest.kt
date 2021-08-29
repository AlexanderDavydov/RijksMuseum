package ru.alexandro.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import ru.alexandro.domain.model.ArtObjectDetail
import ru.alexandro.domain.repository.ArtObjectRepository
import ru.alexandro.domain.usecase.artobject.RetrieveArtObjectDetailUseCase

class RetrieveArtObjectDetailUseCaseTest {

    private lateinit var objectRepository: ArtObjectRepository

    private lateinit var retrieveArtObjectDetailUseCase: RetrieveArtObjectDetailUseCase

    @Test
    fun `Test Retrieve ArtObjectDetail Success`() = runBlockingTest {

        val testObjectNumber = "cnjdowqok"
        val testDetailedObject: ArtObjectDetail = mockk(relaxed = true)

        objectRepository = mockk {
            coEvery { getArtObjectDetail(testObjectNumber) } returns testDetailedObject
        }
        retrieveArtObjectDetailUseCase = RetrieveArtObjectDetailUseCase(objectRepository)


        val result =
            retrieveArtObjectDetailUseCase.executeAsync(this, testObjectNumber).await()


        Assert.assertEquals(testDetailedObject, result)
    }

}