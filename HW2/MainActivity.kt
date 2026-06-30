package com.example.guessnumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GuessNumberScreen()
                }
            }
        }
    }
}

@Composable
fun GuessNumberScreen() {
    var secretNumber by remember { mutableStateOf(Random.nextInt(0, 101)) }
    var userInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var isGuessed by remember { mutableStateOf(false) }

    fun resetGame() {
        secretNumber = Random.nextInt(0, 101)
        userInput = ""
        message = null
        isGuessed = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!isGuessed) {
            Text(
                text = message ?: "Угадай число от 0 до 100",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (isGuessed) {
            AsyncImage(
                model = "https://picsum.photos/300/300",
                contentDescription = "Победа",
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = ::resetGame) {
                Text("Ещё раз")
            }
        }

        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val number = userInput.toIntOrNull()
                if (number == null) {
                    message = "Вводи только цифры!"
                    return@Button
                }
                if (number < 0 || number > 100) {
                    message = "Число должно быть от 0 до 100"
                    return@Button
                }
                if (number < secretNumber) {
                    message = "Меньше"
                } else if (number > secretNumber) {
                    message = "Больше"
                } else {
                    isGuessed = true
                    message = null
                }
            },
            enabled = !isGuessed,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить")
        }
    }
}
