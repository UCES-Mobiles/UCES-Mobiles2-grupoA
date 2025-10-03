package com.example.espncito.network.viewByTeam

import com.example.espncito.network.common.RetrofitFactory.RetrofitFactory


object ViewByTeamRetrofitClient {
    private const val BASE_URL = "https://site.api.espn.com/"

    val viewByTeamService: ViewByTeamService by lazy {
        RetrofitFactory.createService(BASE_URL, ViewByTeamService::class.java)
    }
}