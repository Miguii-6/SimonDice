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


class MyViewModel: ViewModel(){
    //declaramos TAG_LOG
    private val tag = "corutina"


    fun startGame() {
        Log.d(tag, "Iniciando o xogo")
        Data.ronda.value = 0
        Data.secuence = mutableListOf()
        Data.secuenceUser = mutableListOf()
        Data.estado = Data.State.START
    }



    fun changeState() {
        Log.d(tag, "Cambia o estado da aplicación")

        Data.estado = when (Data.estado) {
            Data.State.START -> Data.State.SEQUENCE
            Data.State.SEQUENCE -> Data.State.WAITING
            Data.State.WAITING -> Data.State.CHECKING
            Data.State.CHECKING -> Data.State.FINISHED
            Data.State.FINISHED -> Data.State.START

        }

        getState() // Chamar a función getState()
    }

    private fun getState(): Data.State {
        Log.d(tag, "O estado actual é:${Data.estado}")
        return Data.estado

    }

    private fun generarNumeroAleatorio(): Int {
        return (0..3).random()

    }

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

    private fun darkestColor(color: Color): Color {
        val r = (color.red * (1 - 0.5f)).coerceIn(0f, 1f)
        val g = (color.green * (1 -  0.5f)).coerceIn(0f, 1f)
        val b = (color.blue * (1 -  0.5f)).coerceIn(0f, 1f)
        return Color(r, g, b, color.alpha)
    }

    //Funcion para xenerar unha secuencia:
    // Variable para habilitar/deshabilitar botons
    var buttonsEnabled by mutableStateOf(true)

    // Función para desactivar os botones
    private fun disableButtons() {
        buttonsEnabled = false
    }

    // Función para habilitar os botones
    private fun enableButtons() {
        buttonsEnabled = true
    }

    // Función para xenerar unha secuencia
    fun generarSecuencia() {
        Log.d(tag, "Generar Secuencia")
        Log.d(tag, "Estado actual: ${Data.estado}")
        Data.secuence.add(generarNumeroAleatorio())
        Log.d(tag, "Secuencia generada: ${Data.secuence}")

        disableButtons()

        viewModelScope.launch {

            for (i in Data.secuence) {
                Log.d(tag, "Mostramos o color $i")
                Data.colorPath = Data.numColores[i].color.value
                Data.numColores[i].color.value = darkestColor(Data.colorPath)
                delay(400)
                Data.numColores[i].color.value = Data.colorPath
                delay(400)
            }
            enableButtons()
        }
    }

    fun aumentarSecuencia(){
        if (Data.estado == Data.State.SEQUENCE){
            generarSecuencia()
            Data.estado = Data.State.WAITING
            Log.d(tag, "Cambia el estado a ${Data.estado}")

            //Ahora reseteamos la secuencia del usuario
            Data.secuenceUser = mutableListOf()

        }
    }
    fun guardarSecuenciaUsuario(color: Int) {
        Data.secuenceUser.add(color)
        Log.d(tag, "Secuencia del usuario: ${Data.secuenceUser}")

    }

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