package com.example.espncito.ui.viewByTeam;

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appparcial2.model.News
import com.example.espncito.databinding.ActivityViewByTeamBinding
import com.example.espncito.model.ViewByTeamModel
import com.example.espncito.network.viewByTeam.ViewByTeamRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewByTeamActivity : AppCompatActivity() {

    private val TAG = "viewViewByTeamLog"
    private lateinit var binding: ActivityViewByTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityViewByTeamBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        getVBT()
    }

    private fun getVBT() {
        val call = ViewByTeamRetrofitClient.viewByTeamService.getViewByTeam()
        try {
            call.enqueue(object : Callback<ViewByTeamModel> {
                override fun onResponse(
                    call: Call<ViewByTeamModel>,
                    response: Response<ViewByTeamModel>
                ) {
                    if (response.isSuccessful) {
                        val news = response.body()
                        if (news != null) {
                            getLogs(news)
                        } else {
                            Log.i(TAG, "response empty")
                        }
                    } else {
                        Log.i(TAG, "${response.code()} - ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<ViewByTeamModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }catch (e: Exception){
            Log.e(TAG, "Excepción: ${e.message}")}
    }

    private fun getLogs(resp: ViewByTeamModel){
        Log.i(TAG,"titulo:")
    }
}
