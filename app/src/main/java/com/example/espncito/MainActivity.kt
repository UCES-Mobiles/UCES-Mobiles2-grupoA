package com.example.espncito

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appparcial2.model.NoticiasActuales
import com.example.espncito.databinding.ActivityMainBinding
import com.example.espncito.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val TAG = "ESPNapi"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        getNews()
    }

    private fun getNews(){
        val call = RetrofitClient.apiService.getNoticia()

        call.enqueue(object: Callback<NoticiasActuales> {
            override fun onResponse(
                call: Call<NoticiasActuales>,
                response: Response<NoticiasActuales>
            ) {
                if(response.isSuccessful){
                    val news = response.body()
                    if(news!= null){
                        logEspnInfo(news)
                    }else{
                        Log.i(TAG,"esta vasio pero dio 200/300")
                    }
                }else{
                    Log.i(TAG,"${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NoticiasActuales>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun logEspnInfo(news: NoticiasActuales){
        Log.i(TAG,"titulo: ${news.title}")
    }
}