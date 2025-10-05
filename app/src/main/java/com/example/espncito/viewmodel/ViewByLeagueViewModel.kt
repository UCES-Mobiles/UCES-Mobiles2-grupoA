package com.example.espncito.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.espncito.model.TeamInfo
import com.example.espncito.model.ViewByLeagueModel
import com.example.espncito.network.viewByLeague.ViewByLeagueRetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

sealed class TeamsState {
    object Loading : TeamsState()
    data class Success(val teams: List<TeamInfo>) : TeamsState()
    data class Error(val message: String) : TeamsState()
    object Empty : TeamsState()
    object Initial : TeamsState()
}

class ViewByLeagueViewModel : ViewModel() {

    private val _teamsState = MutableLiveData<TeamsState>(TeamsState.Initial)
    val teamsState: LiveData<TeamsState> = _teamsState

    fun fetchTeams(sport: String, league: String) {
        _teamsState.value = TeamsState.Loading

        viewModelScope.launch {
            try {
                // Usar withContext para ejecutar la llamada Retrofit en el dispatcher de IO
                val call = ViewByLeagueRetrofitClient.viewByLeagueService.getTeamsByLeague(sport, league)

                // Usar callback para manejar la respuesta de Retrofit
                call.enqueue(object : Callback<ViewByLeagueModel> {
                    override fun onResponse(
                        call: Call<ViewByLeagueModel>,
                        response: Response<ViewByLeagueModel>
                    ) {
                        if (response.isSuccessful) {
                            val viewByLeagueModel = response.body()
                            if (viewByLeagueModel != null && viewByLeagueModel.sports.isNotEmpty()) {
                                val teams = extractTeamsFromResponse(viewByLeagueModel)
                                _teamsState.value = if (teams.isNotEmpty()) {
                                    TeamsState.Success(teams)
                                } else {
                                    TeamsState.Empty
                                }
                            } else {
                                _teamsState.value = TeamsState.Empty
                            }
                        } else {
                            _teamsState.value = TeamsState.Error("Error: ${response.code()} - ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<ViewByLeagueModel>, t: Throwable) {
                        _teamsState.value = when (t) {
                            is IOException -> TeamsState.Error("Network error: Please check your internet connection")
                            else -> TeamsState.Error("Unexpected error: ${t.message}")
                        }
                    }
                })

            } catch (e: Exception) {
                _teamsState.value = TeamsState.Error("Error: ${e.message}")
            }
        }
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