package com.example.espncito.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.espncito.model.Stat
import com.example.espncito.model.Team
import com.example.espncito.model.ViewByTeamModel
import com.example.espncito.network.viewByTeam.ViewByTeamRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewByTeamViewModel : ViewModel() {

    // LiveData para exponer el equipo y sus datos
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team> = _team

    // LiveData para exponer los stats filtrados (puede ser la lista de stats de record)
    private val _stats = MutableLiveData<List<Stat>>()
    val stats: LiveData<List<Stat>> = _stats

    // LiveData para exponer errores
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Función que llama a la API y actualiza los LiveData
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
                        _team.value = body.team
                        // Obtengo los stats del primer item en record.items (puede cambiar según tu lógica)
                        _stats.value = body.team.record?.items?.firstOrNull()?.stats ?: emptyList()
                    } else {
                        _error.value = "Respuesta vacía"
                    }
                } else {
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            }

            override fun onFailure(call: Call<ViewByTeamModel>, t: Throwable) {
                _error.value = "Fallo de red: ${t.message}"
            }
        })
    }
}