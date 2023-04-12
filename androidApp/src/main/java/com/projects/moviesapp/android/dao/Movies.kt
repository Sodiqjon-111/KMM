package com.projects.moviesapp.android.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.projects.moviesapp.domain.model.MainMovie

@Entity(tableName = "my_database")
data class Movies(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val releaseDate: String,
    val vote_average: Double,
) {
    constructor(
        mainMovie: MainMovie
    ) : this(
        mainMovie.id,
        mainMovie.title,
        mainMovie.description,
        mainMovie.imageUrl,
        mainMovie.releaseDate,
        mainMovie.vote_average
    )
}
