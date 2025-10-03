package com.example.espncito.network.news

import com.example.espncito.network.common.RetrofitFactory.RetrofitFactory

object NewsRetrofitClient {
    private const val BASE_URL = "https://now.core.api.espn.com/"

    val apiService: NewsService by lazy {
        RetrofitFactory.createService(BASE_URL, NewsService::class.java)
    }
}
