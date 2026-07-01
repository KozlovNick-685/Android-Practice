package com.example.homework3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.homework3.ui.theme.*
import com.example.homework3.ui.theme.HomeWork3Theme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.homework3.data.DataRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeWork3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var showSecondScreen by remember { mutableStateOf(false) }

    if (showSecondScreen) {
        // Второй экран
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Заголовок с кнопкой "Назад"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📽️ Список фильмов",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
                Button(onClick = { showSecondScreen = false }) {
                    Text("← Назад")
                }
            }

            SecondScreen()
        }
    } else {
        // Первый экран с кнопкой перехода
        FirstScreen(
            onNavigateToSecond = { showSecondScreen = true }
        )
    }
}