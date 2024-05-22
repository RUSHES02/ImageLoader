package com.example.imageloader.retrofit

import com.example.imageloader.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

//    private val gson: Gson = GsonBuilder()
//        .registerTypeAdapter(Images::class.java, ImageDeserializer())
//        .create()

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Client-ID ${BuildConfig.API_KEY}")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: ImageApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApi::class.java)
    }
}