package com.example.imageloader.retrofit

import com.example.imageloader.BuildConfig
import com.example.imageloader.modals.Images
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageApi {

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("order_by") orderBy: String = "latest",
        @Query("w") w: Int = 50,
        @Query("h") h: Int = 50
//        @Query("count") count: Int = 2
    ): Response<List<Images>>
}