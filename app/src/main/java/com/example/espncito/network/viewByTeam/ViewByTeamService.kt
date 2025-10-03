package com.example.espncito.network.viewByTeam


import com.example.espncito.model.ViewByTeamModel
import retrofit2.Call
import retrofit2.http.GET

interface ViewByTeamService {
    @GET("v1/sports/news?limit=10")
    fun getViewByTeam(): Call<ViewByTeamModel>
}