package com.example.espncito.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val NEWFEED = "https://now.core.api.espn.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NEWFEED)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: EspnApiService by lazy {
        retrofit.create(EspnApiService::class.java)
    }



}