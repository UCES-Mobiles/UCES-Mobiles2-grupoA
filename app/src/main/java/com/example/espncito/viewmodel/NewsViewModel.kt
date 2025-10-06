package com.example.espncito.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appparcial2.model.Headline
import com.example.espncito.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _newsList = MutableLiveData<List<Headline>>()
    val newsList: LiveData<List<Headline>> = _newsList

    fun apiNews() {
        // Obtenemos el LiveData directamente del repo
        val liveDataFromRepo = repository.getNewsLiveData()

        // Observamos los cambios y actualizamos _newsList
        liveDataFromRepo.observeForever { news ->
            _newsList.postValue(news)
        }
    }
}
