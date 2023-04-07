package com.projects.moviesapp.android.favourite

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.projects.moviesapp.android.R
import com.projects.moviesapp.android.dao.DatabaseManager
import com.projects.moviesapp.android.dao.MovieDao
import com.projects.moviesapp.android.dao.Movies
import com.projects.moviesapp.android.home.HomeViewModel
import com.projects.moviesapp.domain.model.MainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("RememberReturnType")
@Composable
fun FavouriteListItem(
    modifier: Modifier = Modifier,
    movie: MainMovie,
    onMovieClick: (MainMovie) -> Unit,
    viewModel: HomeViewModel,


    ) {
    val context = LocalContext.current
    val myDao = remember { DatabaseManager.getInstance(context).movieDao() }

    Card(
        modifier = modifier
            .height(220.dp)
            .clickable { onMovieClick(movie) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Box(
                modifier = modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = movie.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 2.dp, bottomEnd = 2.dp))
                )

                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = modifier
                        .size(50.dp),
                    shape = CircleShape
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.play_button),
                        contentDescription = null,
                        modifier = modifier
                            .padding(18.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = modifier.padding(10.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = modifier.height(4.dp))

                    Text(text = movie.releaseDate, style = MaterialTheme.typography.caption)
                }
                MyButton(
                    movie = movie,
                    myDao = myDao,
                    isFavourite = movie.isFavourite
                )

            }
        }
    }


}


@Composable
fun MyButton(movie: MainMovie, myDao: MovieDao, isFavourite: Boolean) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = {
            /* handle click event */
            if (!isFavourite) {
                deleteMovie(movie = movie, myDao = myDao, lifecycleOwner)

            } else {
                // deleteMovie(movie = movie, myDao = myDao, lifecycleOwner)

            }
            getAllMovie(movie = movie, myDao = myDao, lifecycleOwner)


        }
        ) {
            if (isFavourite) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    Modifier.size(36.dp),
                    tint = Color.Red,

                    )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    Modifier.size(36.dp),
                    tint = Color.White,

                    )
            }


        }
    }
}


fun insertToRoom(movie: MainMovie, myDao: MovieDao, lifecycleOwner: LifecycleOwner) {
    val coroutineScope = lifecycleOwner.lifecycleScope
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            val moviess= Movies(movie)
            myDao.insert(moviess)
            println("id---------insert------------: ${movie.id}, name--------------------: ${movie.title}")
        }
    }


}

fun deleteMovie(movie: MainMovie, myDao: MovieDao, lifecycleOwner: LifecycleOwner) {
    val coroutineScope = lifecycleOwner.lifecycleScope
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            val moviess= Movies(movie)
            myDao.deleteMovie(moviess)
            println("id----------delete-----------: ${movie.id}, name--------------------: ${movie.title}")
        }
    }


}

fun getAllMovie(movie: MainMovie, myDao: MovieDao, lifecycleOwner: LifecycleOwner) {
    val coroutineScope = lifecycleOwner.lifecycleScope
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            myDao.getAllMovies()
            println("id----------getAll-----------: ${myDao.getAllMovies().size}, name--------------------: ${movie.title}")
        }
    }
}
