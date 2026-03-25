package com.example.aplikasijkt48.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/photos")
    suspend fun getPublicPhotos(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 8,
        @Query("source") source: String? = null,
        @Query("nickname") nickname: String? = null,
        @Query("mode") mode: String? = null,
        @Query("search") search: String? = null,
        @Query("post_url") postUrl: String? = null,
        @Query("sort") sort: String? = null
    ): GalleryResponse
}

object ApiClient {
    private const val BASE_URL = "http://192.168.1.7:3000/"

    val instance: ApiService by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
}