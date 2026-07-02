package com.example.homework3.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework3.data.DataRepository
import com.example.homework3.utils.RandomUtils

@Composable
fun FirstScreen(
    onNavigateToSecond: () -> Unit = {}  
) {
    var inputText by remember { mutableStateOf("") }
    var isValidNumber by remember { mutableStateOf(false) }
    var generateButtonEnabled by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("Введите положительное число") }

    fun onTextChange(newText: String) {
        inputText = newText
        val number = newText.toIntOrNull()
        isValidNumber = number != null && number > 0
        generateButtonEnabled = isValidNumber
        statusMessage = if (isValidNumber) {
            "Число $number корректно! Нажмите 'Сгенерировать'"
        } else {
            "Введите положительное число"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Генератор фильмов",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { onTextChange(it) },
            label = { Text("Введите положительное число") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = statusMessage,
            color = if (isValidNumber) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    val count = inputText.toInt()
                    val newFilms = RandomUtils.generateFilmsList(count)
                    DataRepository.clearFilms()
                    DataRepository.addFilms(newFilms)
                    statusMessage = "Сгенерировано $count фильмов!"
                },
                enabled = generateButtonEnabled,
                modifier = Modifier.weight(1f)
            ) {
                Text("Сгенерировать")
            }

            Button(
                onClick = {
                    DataRepository.clearFilms()
                    statusMessage = "Список очищен!"
                },
                enabled = DataRepository.filmsList.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Очистить")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Текущее состояние:",
                    fontWeight = FontWeight.Bold
                )
                Text("Фильмов в репозитории: ${DataRepository.filmsList.size}")
                if (DataRepository.filmsList.isNotEmpty()) {
                    Text("Последний добавленный: ${DataRepository.filmsList.last().name}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToSecond,
            enabled = DataRepository.filmsList.isNotEmpty(),  // Активна только если есть фильмы
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📋 Перейти к списку фильмов (${DataRepository.filmsList.size})")
        }
    }
}
