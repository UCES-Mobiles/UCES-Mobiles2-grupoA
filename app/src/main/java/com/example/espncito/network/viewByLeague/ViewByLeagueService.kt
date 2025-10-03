package com.example.espncito.network.viewByLeague

import com.example.espncito.model.ViewByLeagueModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewByLeagueService {
    @GET("apis/site/v2/sports/{sport}/{league}/teams")
    fun getTeamsByLeague(
        @Path("sport") sport: String,
        @Path("league") league: String
    ): Call<ViewByLeagueModel>
}