package com.projects.moviesapp.data.util

import com.projects.moviesapp.data.remote.MovieRemote
import com.projects.moviesapp.domain.model.MainMovie


internal fun MovieRemote.toMovie(): MainMovie {
    return MainMovie(
        id = id,
        title = title,
        description = overview,
        imageUrl = getImageUrl(posterImage),
        releaseDate = releaseDate,
        vote_average=vote_average
    )
}

private fun getImageUrl(posterImage: String) = "https://image.tmdb.org/t/p/w500/$posterImage"