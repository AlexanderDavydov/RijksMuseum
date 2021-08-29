package ru.alexandro.data.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.alexandro.data.mapping.response.ArtObjectDetailResponce
import ru.alexandro.data.mapping.response.ArtObjectResponse

interface ArtObjectApi {

    @GET("api/{culture}/collection")
    fun getArtObjectList(
        @Path("culture") culture: String,
        @Query("p") p: Int,
        @Query("pa") pa: Int,
        @Query("s") s: String = "artist",
        @Query("imgonly") imgOnly: Boolean = true
    ): Deferred<ArtObjectResponse>

    @GET("api/{culture}/collection/{object-number}")
    fun getArtObjectDetail(
        @Path("culture") culture: String,
        @Path("object-number") objectNumber: String
    ): Deferred<ArtObjectDetailResponce>

}