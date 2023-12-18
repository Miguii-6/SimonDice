package com.example.simondice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.simondice.ui.theme.Greeting
import com.example.simondice.ui.theme.SimonDiceTheme
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimonDiceTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    Greeting(miModel = MyViewModel()) // Pasando el ViewModel a la interfaz

                }
            }
        }
    }
}

