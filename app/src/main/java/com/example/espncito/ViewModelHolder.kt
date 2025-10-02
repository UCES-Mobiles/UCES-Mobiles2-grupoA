// ViewModelHolder.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.espncito.viewmodel.NoticiasViewModel

object ViewModelHolder {
    // Almacén único GLOBAL para ViewModels
    private val globalViewModelStore = ViewModelStore()

    // Instancia única del ViewModel
    private val deportesViewModel by lazy {
        ViewModelProvider(globalViewModelStore, DeportesViewModelFactory()).get(NoticiasViewModel::class.java)
    }

    // Función para obtener la instancia compartida
    fun getSharedDeportesViewModel(): NoticiasViewModel {
        return deportesViewModel
    }
}

// Factory para crear el ViewModel
class DeportesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { /*class T es de tipo genérico o heredada de ViewModel*/
        if (modelClass.isAssignableFrom(NoticiasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") /*para evitar que el compilador se queje del cast a tipo genérico T */
            return NoticiasViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}