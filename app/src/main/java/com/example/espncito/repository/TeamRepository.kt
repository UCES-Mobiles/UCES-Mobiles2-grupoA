package com.example.espncito.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.espncito.model.Team
import com.example.espncito.model.ViewByTeamModel
import com.example.espncito.network.viewByTeam.ViewByTeamRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamRepository {

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team> get() = _team

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchTeam(sport: String, league: String, teamId: Int) {
        val call = ViewByTeamRetrofitClient.viewByTeamService.getTeamById(sport, league, teamId)

        call.enqueue(object : Callback<ViewByTeamModel> {
            override fun onResponse(
                call: Call<ViewByTeamModel>,
                response: Response<ViewByTeamModel>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _team.postValue(body.team)
                    } else {
                        _error.postValue("Respuesta vacía")
                    }
                } else {
                    _error.postValue("Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ViewByTeamModel>, t: Throwable) {
                _error.postValue("Fallo de red: ${t.message}")
            }
        })
    }
}