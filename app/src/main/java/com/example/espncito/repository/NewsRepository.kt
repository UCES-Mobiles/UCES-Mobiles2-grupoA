package com.example.espncito.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appparcial2.model.Headline
import com.example.appparcial2.model.NewsModel
import com.example.espncito.network.news.NewsRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    private val TAG = "NewsRepository"

    fun getNewsLiveData(): LiveData<List<Headline>> {
        val newsLiveData = MutableLiveData<List<Headline>>()

        val call = NewsRetrofitClient.apiService.getNews()
        call.enqueue(object : Callback<NewsModel> {
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                if (response.isSuccessful) {
                    val news = response.body()?.headlines ?: emptyList()
                    newsLiveData.postValue(news)
                } else {
                    Log.e(TAG, "Error ${response.code()} - ${response.message()}")
                    newsLiveData.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                Log.e(TAG, "Excepción: ${t.message}")
                newsLiveData.postValue(emptyList())
            }
        })

        return newsLiveData
    }

    private fun logEspnInfo(news: NewsModel) {
        Log.i(TAG, "titulo: ${news.headlines.get(1).title}")
    }
}



