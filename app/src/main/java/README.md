# SimonDice Game App

Esta aplicación implementa el famoso juego "Simon Dice" usando Jetpack Compose y Kotlin. El juego consiste en una secuencia de colores que el usuario debe recordar y replicar.

## Estructura del Código

El código de la aplicación está organizado en distintos paquetes:

- **`com.example.simondice`**: Contiene la lógica del juego y sus componentes principales.
- **`com.example.simondice.ui.theme`**: Define la apariencia visual de la aplicación.

### `MyViewModel.kt`

Este archivo contiene la lógica principal del juego. Se utiliza un `ViewModel` para gestionar el estado y las acciones del juego.

- **`startGame()`**: Inicia el juego, restableciendo los valores iniciales.
- **`changeState()`**: Cambia el estado del juego según la lógica definida.
- **`generarSecuencia()`**: Genera una secuencia aleatoria de colores para que el usuario la reproduzca.
- **`aumentarSecuencia()`**: Aumenta la secuencia si el estado es `SEQUENCE`.
- **`guardarSecuenciaUsuario()`**: Guarda la secuencia ingresada por el usuario.
- **`comprobarSecuencia()`**: Verifica si la secuencia ingresada es correcta.

### `Data.kt`

En este archivo se almacenan los datos del juego, como la puntuación, las secuencias y el estado actual.

### `UI Components`

Los componentes de la interfaz de usuario (`Greeting`, `Record`, `BotonsColores`, `Boton`, `StartButton`, `Enviar`, etc.) están definidos en varios archivos dentro del paquete `com.example.simondice.ui.theme`. Estos componentes se componen utilizando Jetpack Compose para construir la interfaz de usuario del juego.

## Migración a Arquitectura MVVM

Para migrar este código a la arquitectura MVVM (Model-View-ViewModel), sigue estos pasos:

1. **Crear el Modelo (`Data.kt`)**:
    - Mantén la clase `Data` para almacenar los datos del juego.

2. **Crear el ViewModel (`MyViewModel.kt`)**:
    - Asegúrate de que la lógica del juego se encuentra en el ViewModel (`MyViewModel`), separando la lógica de la interfaz de usuario.

3. **Modificar la Interfaz de Usuario (`UI Components`)**:
    - Los componentes de la interfaz de usuario (`@Composable`) deben interactuar con el ViewModel a través de los métodos y estados definidos en él, en lugar de acceder directamente a los datos.

4. **Observar el Estado (`LiveData` o `StateFlow`)**:
    - Utiliza `LiveData` o `StateFlow` para exponer los estados y datos del ViewModel a los componentes de la interfaz de usuario, asegurándote de que se actualicen correctamente.

5. **Manejar Eventos (`ViewModelScope`)**:
    - Utiliza `viewModelScope` para realizar operaciones asíncronas y eventos relacionados con la lógica del juego.

---

Este README proporciona una visión general del código de la aplicación SimonDice, describe su estructura y ofrece una guía básica para migrar la lógica a la arquitectura MVVM.
