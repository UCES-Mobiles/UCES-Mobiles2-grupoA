package com.example.espncito.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.example.appparcial2.model.News
import com.example.espncito.databinding.ActivityMainBinding
import com.example.espncito.network.news.NewsRetrofitClient
import com.example.espncito.ui.viewByTeam.ViewByTeamActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {

    private val TAG = "espnApiLogs"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        getNews()


    }
    private fun getNews() {
        val call = NewsRetrofitClient.apiService.getNews()
        try {
            call.enqueue(object : Callback<News> {
                override fun onResponse(
                    call: Call<News>,
                    response: Response<News>
                ) {
                    if (response.isSuccessful) {
                        val news = response.body()
                        if (news != null) {
                            logEspnInfo(news)
                        } else {
                            Log.i(TAG, "response empty")
                        }
                    } else {
                        Log.i(TAG, "${response.code()} - ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<News>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }catch (e: Exception){
            Log.e(TAG, "Excepción: ${e.message}")}
    }
    private fun logEspnInfo(news: News){
        Log.i(TAG,"titulo: ${news.headlines.get(1).title}")
    }
}