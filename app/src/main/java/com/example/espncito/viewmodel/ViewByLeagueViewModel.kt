package com.example.espncito.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.espncito.model.TeamInfo
import com.example.espncito.repository.LeagueRepository
import kotlinx.coroutines.launch

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

    // Instancia del repository
    private val leagueRepository = LeagueRepository()

    fun fetchTeams(sport: String, league: String) {
        _teamsState.value = TeamsState.Loading

        viewModelScope.launch {
            leagueRepository.fetchTeams(sport, league) { result ->
                when {
                    result.isSuccess -> {
                        val teams = result.getOrNull() ?: emptyList()
                        _teamsState.value = if (teams.isNotEmpty()) {
                            TeamsState.Success(teams)
                        } else {
                            TeamsState.Empty
                        }
                    }
                    result.isFailure -> {
                        val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                        _teamsState.value = TeamsState.Error(errorMessage)
                    }
                }
            }
        }
    }
}