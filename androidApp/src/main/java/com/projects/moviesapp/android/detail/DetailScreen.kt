package com.projects.moviesapp.android.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.projects.moviesapp.android.R
import com.projects.moviesapp.android.Red

@Composable
fun DetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    uiState: DetailScreenState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movie Details")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
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
                uiState.movie?.let { movie ->
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
                            Row (){
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.Bold
                                )

                            }
                            Spacer(modifier = modifier.height(8.dp))

                            Button(
                                onClick = {},
                                modifier = modifier
                                    .fillMaxWidth()
                                    .height(46.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Red
                                ),
                                elevation = ButtonDefaults.elevation(
                                    defaultElevation = 0.dp
                                )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.play_button),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = modifier.width(8.dp))

                                Text(text = "Start watching now", color = Color.White)
                            }

                            Spacer(modifier = modifier.height(16.dp))

                            Text(
                                text = "Released in ${movie.releaseDate}".uppercase(),
                                style = MaterialTheme.typography.overline
                            )

                            Spacer(modifier = modifier.height(4.dp))

                            Text(text = movie.description, style = MaterialTheme.typography.body2)
                            Spacer(modifier = modifier.height(16.dp))
//                            Row (    modifier = modifier.fillMaxSize(),
//                                horizontalArrangement = Arrangement.Center){
//                                Icon(
//                                    painter = painterResource(id = R.drawable.add),
//                                    contentDescription = null,
//                                    Modifier.size(60.dp),
//                                    tint = Color.White
//                                )
//                            }
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

