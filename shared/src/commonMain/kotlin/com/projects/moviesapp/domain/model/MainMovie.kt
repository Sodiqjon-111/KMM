package com.projects.moviesapp.domain.model

@kotlinx.serialization.Serializable
data class MainMovie(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val releaseDate: String,
)
