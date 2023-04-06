package com.projects.moviesapp.android.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.projects.moviesapp.android.R
import com.projects.moviesapp.android.dao.DatabaseManager
import com.projects.moviesapp.android.dao.MovieDao
import com.projects.moviesapp.android.dao.Movies
import com.projects.moviesapp.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("RememberReturnType")
@Composable
fun MovieListItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    viewModel: HomeViewModel,
) {
    val context = LocalContext.current
    val myDao = remember { DatabaseManager.getInstance(context).movieDao() }
//    LaunchedEffect(movie) {
//        withContext(Dispatchers.IO) {
//            val myEntity = Movies(50, "John")
//            myDao.insert(myEntity)
//            val myEntities = myDao.getAllMovies()
//            myEntities.forEach {
//                println("id---------------------: ${it.id}, name--------------------: ${it.name}")
//            }
//        }
//    }
    //  myDao.insert(Movies(2,"kalla"))

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
                    movie = Movies(20, "Sodiqjon"),
                    myDao = myDao,
                    isFavourite = movie.isFavourite
                )
//                Box(
//                    modifier = Modifier
//                        .padding(10.dp)
//                        .fillMaxWidth()
//                ) {
//                    IconButton(
//                        modifier = Modifier.align(Alignment.CenterEnd),
//                        onClick = {
//                            if (!movie.isFavourite) {
//                                movie.isFavourite = true
//                             //   insertToRoom()
//                            } else {
//
//                                movie.isFavourite = false
//
//                            }
//                            Log.d(TAG, "8888888888 Favourite  ${viewModel.favouriteList}")
//                        },
//                    )
//                    {
//                        if (movie.isFavourite) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.favourite),
//                                contentDescription = null,
//                                Modifier.size(36.dp),
//                                tint = Color.Red,
//
//                                )
//                        } else {
//                            Icon(
//                                painter = painterResource(id = R.drawable.favourite),
//                                contentDescription = null,
//                                Modifier.size(36.dp),
//                                tint = Color.White,
//
//                                )
//                        }
//
//                    }
//                }


            }
        }
    }


}


@Composable
fun MyButton(movie: Movies, myDao: MovieDao, isFavourite: Boolean) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = {
            /* handle click event */
            if (!isFavourite) {
                insertToRoom(movie = movie, myDao = myDao, lifecycleOwner)

            } else {
               // deleteMovie(movie = movie, myDao = myDao, lifecycleOwner)

            }
            getAllMovie(movie = movie, myDao = myDao, lifecycleOwner)


        }
        ) {
            if (isFavourite) {
                Icon(
                    painter = painterResource(id = R.drawable.favourite),
                    contentDescription = null,
                    Modifier.size(36.dp),
                    tint = Color.Red,

                    )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.favourite),
                    contentDescription = null,
                    Modifier.size(36.dp),
                    tint = Color.White,

                    )
            }


        }
    }
}


fun insertToRoom(movie: Movies, myDao: MovieDao, lifecycleOwner: LifecycleOwner) {
    val coroutineScope = lifecycleOwner.lifecycleScope
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            myDao.insert(movie)
            println("id---------insert------------: ${movie.id}, name--------------------: ${movie.name}")
        }
    }


}

//fun deleteMovie(movie: Movies, myDao: MovieDao, lifecycleOwner: LifecycleOwner) {
//    val coroutineScope = lifecycleOwner.lifecycleScope
//    coroutineScope.launch {
//        withContext(Dispatchers.IO) {
//            myDao.deleteMovie(movie)
//            println("id----------delete-----------: ${movie.id}, name--------------------: ${movie.name}")
//        }
//    }
//
//
//}

fun getAllMovie(movie: Movies, myDao: MovieDao, lifecycleOwner: LifecycleOwner) {
    val coroutineScope = lifecycleOwner.lifecycleScope
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            myDao.getAllMovies()
            println("id----------getAll-----------: ${myDao.getAllMovies().size}, name--------------------: ${movie.name}")
        }
    }
}




