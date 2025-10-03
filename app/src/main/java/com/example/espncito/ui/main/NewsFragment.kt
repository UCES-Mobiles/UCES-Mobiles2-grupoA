package com.example.espncito.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appparcial2.model.News
import com.example.espncito.databinding.FragmentNewsBinding
import com.example.espncito.network.news.NewsRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "NewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNews()
    }

    private fun getNews() {
        val call = NewsRetrofitClient.apiService.getNews()
        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
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
                Log.e(TAG, "Excepción: ${t.message}")
            }
        })
    }

    private fun logEspnInfo(news: News) {
        Log.i(TAG, "titulo: ${news.headlines.get(1).title}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}