package com.example.homework3.data

object DataRepository {
    var filmsList: MutableList<FilmModel> = mutableListOf()
        private set

    fun clearFilms() {
        filmsList.clear()
    }

    fun addFilms(films: List<FilmModel>) {
        filmsList.addAll(films)
    }
}