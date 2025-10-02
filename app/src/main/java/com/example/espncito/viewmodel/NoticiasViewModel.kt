package com.example.espncito.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appparcial2.model.NoticiasActuales

class NoticiasViewModel: ViewModel() {

    private val _noticias = MutableLiveData<List<NoticiasActuales>>()
    val noticias: LiveData<List<NoticiasActuales>> = _noticias

    fun getNoticias(): List<NoticiasActuales> = _noticias.value.orEmpty()
}