package com.projects.moviesapp.android.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.projects.moviesapp.android.R
import com.projects.moviesapp.android.Red
import com.projects.moviesapp.android.colors.fonts

@SuppressLint("UnrememberedMutableState")
@Composable
fun DetailScreen(
    navController: NavController, modifier: Modifier = Modifier, uiState: DetailScreenState
) {


    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Movie Details", fontFamily = fonts
                )

            },

            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            },
            backgroundColor = MaterialTheme.colors.background,
            contentColor = Color.White,
            elevation = 10.dp
        )
    }, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xff8d6e63)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.mainMovie?.let { movie ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background)
                        .padding(0.dp, 6.dp)

                ) {
                    AsyncImage(
                        model = movie.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    )

                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = movie.title,
                                fontFamily = fonts,
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.weight(1f)
                            )
                            RatingBar(
                                rating = movie.vote_average.toInt(),
                            )
                            Spacer(modifier = modifier.width(8.dp))
                            FloatingActionButton(
                                onClick = { /*TODO*/ },
                                shape = CircleShape,
                                elevation = FloatingActionButtonDefaults.elevation(
                                    defaultElevation = 10.dp, focusedElevation = 10.dp
                                ),
                                backgroundColor = Color.Red,

                                ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.play_float),
                                    contentDescription = "",
                                    tint = Color.White,
                                )
                            }


                        }

                        Spacer(modifier = modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(shape = RoundedCornerShape(size = 8.dp))
                                    .background(Color(0xB729282C))
                                    .padding(16.dp),
                            ) {
                                Column(modifier = Modifier.align(Alignment.Center)) {
                                    Text(
                                        text = "Length",
                                        fontFamily = fonts,
                                        color = Color(0xFF7E7E78)
                                    )
                                    Text(
                                        text = "1h 49m",
                                        color = Color.White,
                                        fontFamily = fonts,
                                    )

                                }
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(shape = RoundedCornerShape(size = 8.dp))
                                    .background(Color(0xB729282C))
                                    .padding(16.dp),
                            ) {
                                Column(modifier = Modifier.align(Alignment.Center)) {
                                    Text(
                                        text = "RATING",
                                        fontFamily = fonts,
                                        color = Color(0xFF7E7E78)
                                    )
                                    Text(
                                        text = "8/10",
                                        color = Color.White,
                                        fontFamily = fonts,
                                    )

                                }
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(shape = RoundedCornerShape(size = 8.dp))
                                    .background(Color(0xB729282C))
                                    .padding(16.dp),
                            ) {
                                Column(modifier = Modifier.align(Alignment.Center)) {
                                    Text(
                                        text = "IMDB",
                                        fontFamily = fonts,
                                        color = Color(0xFF7E7E78)
                                    )
                                    Text(
                                        text = "16+",
                                        color = Color.White,
                                        fontFamily = fonts,
                                    )

                                }
                            }


                        }
                        Spacer(modifier = modifier.height(12.dp))

                        Text(
                            text = "Released in ${movie.releaseDate}".uppercase(),
                            fontFamily = fonts,
                            style = MaterialTheme.typography.overline
                        )

                        Spacer(modifier = modifier.height(4.dp))

                        Text(
                            text = movie.description,
                            style = MaterialTheme.typography.body2,
                            fontFamily = fonts,
                        )
                        Spacer(modifier = modifier.height(16.dp))
                    }
                }
            }

            if (uiState.loading) {
                Row(
                    modifier = modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = Red
                    )
                }
            }

        }
    }


    )
}

@Composable
fun RatingBar(rating: Int) {
    Row() {
        repeat(5) {
            val imageResource = if (it < rating / 2) {
                R.drawable.star // filled star image
            } else {
                R.drawable.star_2 // outline star image
            }
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null // content description is not needed
            )
        }
    }
}




