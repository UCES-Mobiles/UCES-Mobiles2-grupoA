package com.example.espncito.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appparcial2.model.News

class NewsViewModel: ViewModel() {

    private val _noticias = MutableLiveData<List<News>>()
    val noticias: LiveData<List<News>> = _noticias

    fun getNoticias(): List<News> = _noticias.value.orEmpty()
}