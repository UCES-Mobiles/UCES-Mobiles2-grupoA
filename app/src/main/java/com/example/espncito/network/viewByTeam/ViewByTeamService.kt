package com.example.espncito.network.viewByTeam

import com.example.espncito.model.ViewByTeamModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewByTeamService {
    @GET("apis/site/v2/sports/{sport}/{league}/teams/{teamId}")
    fun getTeamById(
        @Path("sport") sport: String,
        @Path("league") league: String,
        @Path("teamId") teamId: Int
    ): Call<ViewByTeamModel>
}