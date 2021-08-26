package ru.alexandro.data.api

import kotlinx.coroutines.Deferred
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.alexandro.data.mapping.response.ArtObjectResponse

interface ArtObjectApi {

    @GET("api/{culture}/collection")
    fun getArtObjectList(
        @Path("culture") culture: String,
        @Query("p") p: Int,
        @Query("pa") pa: Int
    ): Deferred<ArtObjectResponse>

}