package com.example.espncito.network.news

import com.example.appparcial2.model.NewsModel
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("v1/sports/news?limit=10")
    fun getNews(): Call<NewsModel>
}