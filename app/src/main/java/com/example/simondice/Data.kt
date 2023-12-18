package com.example.simondice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

object Data {
    var ronda = mutableStateOf(0);
    var secuence = mutableListOf<Int>();
    var secuenceUser = mutableListOf<Int>();
    var score = mutableStateOf(0);
    var estado = State.START;


    var colores = listOf(
        Colors.ROJO.color,
        Colors.AZUL.color,
        Colors.AMARILLO.color,
        Colors.VERDE.color
    )

    //Aun no se para que usar esto
    var numColores = Colors.values()
    var colorPath: Color = Color.White

    enum class State {
        START, SEQUENCE, WAITING, CHECKING, FINISHED
    }

    enum class Colors(val color: MutableState<Color>,  val colorName: String) {
        ROJO(mutableStateOf(Color.Red), "ROJO"),
        AZUL(color = mutableStateOf(Color.Blue), "AZUL"),
        AMARILLO(color = mutableStateOf(Color.Yellow), "AMARILLO"),
        VERDE(color = mutableStateOf(Color.Green), "VERDE"),

    }

}