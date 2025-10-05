package com.example.espncito.repository

import com.example.espncito.model.TeamInfo
import com.example.espncito.model.ViewByLeagueModel
import com.example.espncito.network.viewByLeague.ViewByLeagueRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LeagueRepository {

    fun fetchTeams(sport: String, league: String, callback: (Result<List<TeamInfo>>) -> Unit) {
        val call = ViewByLeagueRetrofitClient.viewByLeagueService.getTeamsByLeague(sport, league)

        call.enqueue(object : Callback<ViewByLeagueModel> {
            override fun onResponse(
                call: Call<ViewByLeagueModel>,
                response: Response<ViewByLeagueModel>
            ) {
                if (response.isSuccessful) {
                    val viewByLeagueModel = response.body()
                    if (viewByLeagueModel != null && viewByLeagueModel.sports.isNotEmpty()) {
                        val teams = extractTeamsFromResponse(viewByLeagueModel)
                        callback(Result.success(teams))
                    } else {
                        callback(Result.success(emptyList())) // Empty list instead of error
                    }
                } else {
                    callback(Result.failure(IOException("Error: ${response.code()} - ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<ViewByLeagueModel>, t: Throwable) {
                val error = when (t) {
                    is IOException -> IOException("Network error: Please check your internet connection")
                    else -> Exception("Unexpected error: ${t.message}")
                }
                callback(Result.failure(error))
            }
        })
    }

    private fun extractTeamsFromResponse(viewByLeagueModel: ViewByLeagueModel): List<TeamInfo> {
        val teams = mutableListOf<TeamInfo>()
        viewByLeagueModel.sports.forEach { sport ->
            sport.leagues.forEach { league ->
                league.teams.forEach { leagueTeam ->
                    teams.add(leagueTeam.team)
                }
            }
        }
        return teams
    }
}