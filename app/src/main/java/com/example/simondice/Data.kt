package com.example.simondice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

// Objeto que almacena datos del juego
object Data {
    // Variables para controlar la ronda, secuencias, puntaje y estado del juego
    var ronda = mutableStateOf(0);
    var secuence = mutableListOf<Int>();
    var secuenceUser = mutableListOf<Int>();
    var score = mutableStateOf(0);
    var estado = State.START;

    // Lista de colores en el juego
    var colores = listOf(
        Colors.ROJO.color,
        Colors.AZUL.color,
        Colors.AMARILLO.color,
        Colors.VERDE.color
    )

    // Variable para almacenar la secuencia de colores seleccionada por la máquina
    var numColores = Colors.values()
    // Variable para almacenar el color actual
    var colorPath: Color = Color.White

    // Enum para los diferentes estados del juego
    enum class State {
        START, SEQUENCE, WAITING, CHECKING, FINISHED
    }

    // Enum para los colores en el juego
    enum class Colors(val color: MutableState<Color>,  val colorName: String) {
        ROJO(mutableStateOf(Color.Red), "ROJO"),
        AZUL(color = mutableStateOf(Color.Blue), "AZUL"),
        AMARILLO(color = mutableStateOf(Color.Yellow), "AMARILLO"),
        VERDE(color = mutableStateOf(Color.Green), "VERDE"),

    }

}