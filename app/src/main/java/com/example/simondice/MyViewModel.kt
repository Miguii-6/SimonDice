package com.example.simondice

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Clase ViewModel para la lógica del juego
class MyViewModel: ViewModel(){
    //declaramos TAG_LOG
    private val tag = "corutina"

    // Función para iniciar el juego
    fun startGame() {
        // Restablece los valores iniciales del juego
        Log.d(tag, "Iniciando o xogo")
        Data.ronda.value = 0
        Data.secuence = mutableListOf()
        Data.secuenceUser = mutableListOf()
        Data.estado = Data.State.START
    }


    // Función para cambiar el estado del juego
    fun changeState() {
        Log.d(tag, "Cambia o estado da aplicación")
        // Cambia el estado del juego según la lógica establecida
        Data.estado = when (Data.estado) {
            Data.State.START -> Data.State.SEQUENCE
            Data.State.SEQUENCE -> Data.State.WAITING
            Data.State.WAITING -> Data.State.CHECKING
            Data.State.CHECKING -> Data.State.FINISHED
            Data.State.FINISHED -> Data.State.START

        }

        getState() // Llama a la función getState()
    }


    // Función para obtener el estado actual del juego
    private fun getState(): Data.State {
        Log.d(tag, "O estado actual é:${Data.estado}")
        return Data.estado

    }


    // Genera un número aleatorio entre 0 y 3
    private fun generarNumeroAleatorio(): Int {
        return (0..3).random()

    }


    // Cambia el color de un botón al ser pulsado
    @SuppressLint("SuspiciousIndentation")
    fun cambiaColorBotonAlPulsar(color: MutableState<Color>) {
        Log.d(tag, "Cambia o color do boton ao pulsar")
        viewModelScope.launch {
            Data.colorPath = color.value
            color.value = darkestColor(Data.colorPath)
            delay(300)
            color.value = Data.colorPath
        }
    }

    // Reduce el brillo del color para dar efecto visual
    private fun darkestColor(color: Color): Color {
        val r = (color.red * (1 - 0.5f)).coerceIn(0f, 1f)
        val g = (color.green * (1 -  0.5f)).coerceIn(0f, 1f)
        val b = (color.blue * (1 -  0.5f)).coerceIn(0f, 1f)
        return Color(r, g, b, color.alpha)
    }


    // Variable para habilitar/deshabilitar botons
    var buttonsEnabled by mutableStateOf(true)

    // Función para deshabilitar los botones
    private fun disableButtons() {
        buttonsEnabled = false
    }


    // Función para habilitar os botones
    private fun enableButtons() {
        buttonsEnabled = true
    }



    // Genera la secuencia de colores del juego
    fun generarSecuencia() {
        Log.d(tag, "Generar Secuencia")
        Log.d(tag, "Estado actual: ${Data.estado}")
        // Agrega un número aleatorio a la secuencia
        Data.secuence.add(generarNumeroAleatorio())
        Log.d(tag, "Secuencia generada: ${Data.secuence}")

        disableButtons() // Deshabilita los botones

        viewModelScope.launch {
            // Muestra la secuencia de colores al usuario
            for (i in Data.secuence) {
                Log.d(tag, "Mostramos o color $i")
                Data.colorPath = Data.numColores[i].color.value
                Data.numColores[i].color.value = darkestColor(Data.colorPath)
                delay(400)
                Data.numColores[i].color.value = Data.colorPath
                delay(400)
            }
            enableButtons() // Habilita los botones
        }
    }


    // Aumenta la secuencia si el estado es SEQUENCE
    fun aumentarSecuencia(){
        if (Data.estado == Data.State.SEQUENCE){
            generarSecuencia()
            Data.estado = Data.State.WAITING
            Log.d(tag, "Cambia el estado a ${Data.estado}")

            //Ahora reseteamos la secuencia del usuario
            Data.secuenceUser = mutableListOf()

        }
    }

    // Guarda la secuencia del usuario
    fun guardarSecuenciaUsuario(color: Int) {
        Data.secuenceUser.add(color)
        Log.d(tag, "Secuencia del usuario: ${Data.secuenceUser}")

    }

    // Comprueba si la secuencia ingresada por el usuario es correcta
    fun comprobarSecuencia(): Boolean {
        val correcta : Boolean
        Data.estado = Data.State.CHECKING
        Log.d(tag, "Cambiamso el estado a ${Data.estado}")
        if (Data.secuence == Data.secuenceUser) {
            Log.d(tag, "OK")
            Data.estado = Data.State.SEQUENCE//Cambiamos el estado a SEQUENCE
            correcta = true
            Data.ronda.value++

            if (Data.ronda.value > Data.score.value) {
                Data.score.value = Data.ronda.value
            }
        } else {
            Log.d(tag, "NOT OK")
            correcta = false
            Data.estado = Data.State.FINISHED
            Log.d(tag, "Se cambia el Estado de la aplicación a${Data.estado}")
            Data.ronda.value = 0
        }
        return correcta
    }


}