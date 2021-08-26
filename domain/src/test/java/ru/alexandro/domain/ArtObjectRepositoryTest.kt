package ru.alexandro.domain

import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.domain.repository.ArtObjectRepository

class ArtObjectRepositoryTest {


    private lateinit var artObjectRepository: ArtObjectRepository

    @Before
    fun setUp() {
        artObjectRepository = mockk(relaxed = true)
    }

    @Test
    fun `get art object list success`() = runBlockingTest{


        coEvery { artObjectRepository.getArtObjectList(query.pageStart, query.numObj) } returns emptyList()

        val slot = slot<List<ArtObject>>()
        coVerify { artObjectRepository.getArtObjectList(query.pageStart, query.numObj) }


        // assertArrayEquals()

    }
}