package com.example.appparcial2.model

import java.io.Serializable

/*enum class Genero {
    ACCION, COMEDIA, DRAMA, DOCUMENTAL, OTRO
}*/


data class NoticiasActuales(
    //val id: Int = generarIdUnico(),
    var titulo: String,
    var desc: String,
    /*var genero: Genero,
    var puntuacion: Int*/
): Serializable {
    companion object {
        private var contador = 0

        fun generarIdUnico(): Int {
            contador++
            return contador
        }
    }
}