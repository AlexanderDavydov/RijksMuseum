package ru.alexandro.rijksmuseum.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit


const val REQUEST_TIMEOUT = 15L // seconds


internal val applicationModule = module {
    single { okHttp() }
    single { retrofit(get()) }
}


private fun okHttp(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor { Timber.d(it) })
        .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .build()
}


@OptIn(ExperimentalSerializationApi::class)
private fun retrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

private const val BASE_URL = "https://www.rijksmuseum.nl"