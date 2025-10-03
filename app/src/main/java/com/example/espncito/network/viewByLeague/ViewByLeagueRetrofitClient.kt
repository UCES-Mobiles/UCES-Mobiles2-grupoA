package com.example.espncito.network.viewByLeague

import com.example.espncito.network.common.RetrofitFactory.RetrofitFactory

object ViewByLeagueRetrofitClient {
    private const val BASE_URL = "https://site.api.espn.com/"

    val viewByLeagueService: ViewByLeagueService by lazy {
        RetrofitFactory.createService(BASE_URL, ViewByLeagueService::class.java)
    }
}