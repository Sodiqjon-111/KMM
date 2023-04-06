package com.projects.moviesapp.android.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.projects.moviesapp.android.R
import com.projects.moviesapp.android.dao.MoviesViewModel
import com.projects.moviesapp.domain.model.Movie
import org.koin.androidx.compose.koinViewModel


@Composable
fun MovieListItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    viewModel: HomeViewModel,
   // favouriteViewModel: FavouriteViewModel,
    //roomViewModel: MoviesViewModel
) {
    val roomViewModel: MoviesViewModel = koinViewModel()
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
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            if (!movie.isFavourite) {
                                viewModel.addToList(movie)
                                movie.isFavourite = true


                            } else {
                                // viewModel.favouriteList.value?.remove(movie)
                               // roomViewModel.insert(movie)
                                viewModel.removeFromList(movie)
                                movie.isFavourite = false

                            }
                            Log.d(TAG, "8888888888 Favourite  ${viewModel.favouriteList}")
                        },
                    )
                    {
                        if (movie.isFavourite) {
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
        }
    }

}
