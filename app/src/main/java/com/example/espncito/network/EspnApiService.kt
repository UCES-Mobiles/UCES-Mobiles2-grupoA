package com.example.espncito.network



import com.example.appparcial2.model.NoticiasActuales
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface EspnApiService {
    /** aca en donde se configurarian las url por el filtro**/
    @GET("v1/sports/news?limit=10")
    fun getNoticia(): Call<NoticiasActuales>



}