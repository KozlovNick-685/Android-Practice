package com.example.homework3.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.example.homework3.data.DataRepository
import com.example.homework3.data.FilmModel

@Composable
fun SecondScreen() {
    // Состояния
    var searchQuery by remember { mutableStateOf("") }
    var filteredFilms by remember { mutableStateOf<List<FilmModel>>(DataRepository.filmsList) }

    // Функция для фильтрации
    fun filterFilms(query: String) {
        if (query.isBlank()) {
            // Если поле пустое - показываем все фильмы
            filteredFilms = DataRepository.filmsList
        } else {
            // Фильтруем по названию фильма (можно изменить на description или другое поле)
            filteredFilms = DataRepository.filmsList.filter { film ->
                film.name.contains(query, ignoreCase = true) ||
                        film.description.contains(query, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок
        Text(
            text = "Список фильмов",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Поле поиска и кнопка
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    // Автоматическая фильтрация при вводе (опционально)
                    // filterFilms(it)
                },
                label = { Text("Поиск по названию") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Button(
                onClick = {
                    filterFilms(searchQuery)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Сортировать")
            }
        }

        // Информация о количестве фильмов
        Text(
            text = "Найдено: ${filteredFilms.size} из ${DataRepository.filmsList.size}",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Список фильмов
        if (filteredFilms.isEmpty()) {
            // Если фильмов нет
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "😕",
                        fontSize = 48.sp
                    )
                    Text(
                        text = "Фильмы не найдены",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Попробуйте изменить запрос",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            // Список фильмов
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredFilms) { film ->
                    FilmItem(film = film)
                }
            }
        }
    }
}

// Компонент для отображения одного фильма
@Composable
fun FilmItem(film: FilmModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Картинка (постер)
            Image(
                painter = rememberAsyncImagePainter(
                    model = film.posterUrl,
                    error = androidx.compose.ui.res.painterResource(
                        id = android.R.drawable.ic_menu_gallery
                    )
                ),
                contentDescription = "Постер фильма ${film.name}",
                modifier = Modifier
                    .width(80.dp)
                    .height(100.dp)
            )

            // Информация о фильме
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = film.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Год: ${film.releaseDate}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = film.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}