// ViewModelHolder.kt
package com.example.espncito.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.espncito.viewmodel.NewsViewModel

object ViewModelHolder {

    // Almacén único GLOBAL para ViewModels
    private val globalViewModelStore = ViewModelStore()

    // Instancia única del NewsViewModel
    private val deportesViewModel: NewsViewModel by lazy {
        ViewModelProvider(globalViewModelStore, DeportesViewModelFactory())
            .get<NewsViewModel>(NewsViewModel::class.java)
    }

    // Función para obtener la instancia compartida
    fun getSharedDeportesViewModel(): NewsViewModel {
        return deportesViewModel
    }
}

// Factory para crear el ViewModel
class DeportesViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
