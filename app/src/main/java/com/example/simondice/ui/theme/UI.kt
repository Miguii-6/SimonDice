package com.example.simondice.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simondice.Data
import com.example.simondice.MyViewModel

@Composable
fun Greeting(miModel: MyViewModel) {
    // Columna principal que contiene los elementos del juego

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Espaciador para separar visualmente los elementos
        Spacer(modifier = Modifier.height(16.dp))
        // Mostrar la ronda actual
        Row {
            Ronda()
            Spacer(modifier = Modifier.width(16.dp))
            Score()

        }
        // Función para mostrar los botones de colores
        BotonsColores(vModel = miModel)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            StartButton(miModel = miModel)
            Spacer(modifier = Modifier.width(16.dp))
            Enviar(miModel = miModel)
        }
    }
}

@Composable
fun  Score(){
    // Mostrar la puntuación record actual
    Text(
        text = "RECORD: ${Data.score.value} ", // Mostrar a puntacion
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    )
}
@Composable
fun Ronda() {
    // Mostrar el número de ronda actual
    Text(
        text = "RONDA: ${Data.ronda.value} ", // Mostrar el número de ronda
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    )

}


@Composable
fun BotonsColores(vModel: MyViewModel) {
    // Agrupa los colores en dos filas
    val colorsInTwoRows = Data.Colors.values().toList().chunked(2)

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Itera sobre las filas de colores
        colorsInTwoRows.forEach { rowColors ->
            Row {
                rowColors.forEach { color ->
                    // Espaciador entre los botones de colores
                    Spacer(modifier = Modifier.width(8.dp))
                    // Botón de color
                    Boton(color = color.color, miModel = vModel, name = color.colorName)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}





@Composable
fun Boton(color: MutableState<Color>, miModel: MyViewModel, name: String) {

    Button(
        onClick = {
            //Recogemos el color que hemos pulsado
            if (Data.estado == Data.State.WAITING && miModel.buttonsEnabled) {
                miModel.guardarSecuenciaUsuario(Data.colores.indexOf(color))
                miModel.cambiaColorBotonAlPulsar(color)
            }
        },
        modifier = Modifier
            .padding(10.dp)
            .size(150.dp),

        colors = ButtonDefaults.buttonColors(color.value)
    ) {
        Text(
            text = name,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}




/**
 * Ahora vamos a crear el boton Start
 */
@Composable
fun StartButton(miModel: MyViewModel) {
    // Botón para iniciar el juego
    Button(
        onClick = {
            // Lógica para iniciar el juego y cambiar el estado
            miModel.startGame()
            miModel.changeState()
            if (Data.estado == Data.State.SEQUENCE ){
                miModel.generarSecuencia()
                miModel.changeState()
            }else{
                miModel.startGame()
            }

        },
        modifier = Modifier
        // Aumentar ligeramente el tamaño del botón
    ) {
        Text(
            text = "START",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun Enviar(miModel: MyViewModel) {
    // Botón para enviar la secuencia
    Button(
        onClick = {
            // Lógica para enviar la secuencia y verificarla
            if (Data.estado == Data.State.WAITING){
                if ( miModel.comprobarSecuencia()) {
                    Log.d("corutina", "Secuencia correcta ")
                    miModel.aumentarSecuencia()
                }else{
                    Log.d("corutina", "Secuencia incorrecta")
                    Data.estado = Data.State.FINISHED
                }

            }


        },
        modifier = Modifier
            .padding(horizontal = 16.dp) // Espacio a los lados del botón
        // Aumentar ligeramente el tamaño del botón
    ) {
        Text(
            text = "ENVIAR",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}