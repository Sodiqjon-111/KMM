package com.projects.moviesapp.android.favourite

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.projects.moviesapp.android.colors.SearchbarColor
import com.projects.moviesapp.android.colors.fonts
import com.projects.moviesapp.android.dao.DatabaseManager
import com.projects.moviesapp.android.dao.MovieDao
import com.projects.moviesapp.android.dao.Movies
import com.projects.moviesapp.domain.model.MainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (MainMovie) -> Unit,
) {
    val context = LocalContext.current
    val myDao = remember { DatabaseManager.getInstance(context).movieDao() }
    val moviesState = remember { mutableStateOf(emptyList<Movies>()) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        var moviesList by remember { mutableStateOf(emptyList<Movies>()) }
        LaunchedEffect(Unit) {
            val myResult = getAllMovieee(myDao = myDao, lifecycleOwner)
            moviesList = myResult
            moviesState.value = myResult
        }
        var searchQuery by remember { mutableStateOf("") }
        SearchBar(searchQuery, onTextChange = { query ->
            searchQuery = query
            moviesState.value = filterItems(searchQuery, moviesList)
        }, onClearText = {
            keyboardController?.hide()
            searchQuery = ""
            moviesState.value = filterItems(searchQuery, moviesList)
        })
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White)

        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                //   Log.d(TAG, "FavouriteScreen:   ${myResult}")
                itemsIndexed(
                    moviesState.value,
                    key = { _, movie -> movie.id }
                ) { index, movie ->
                    FavouriteListItem(
                        movie = movie,
                        onMovieClick = {
                            navigateToDetail(
                                MainMovie(
                                    movie.id,
                                    movie.title,
                                    movie.description,
                                    movie.imageUrl,
                                    movie.releaseDate,
                                    movie.vote_average
                                )
                            )
                        },
                        onDeleteBtn = {
                            deleteMovieItem(movie, myDao, lifecycleOwner) { success ->
                                if (success) {
                                    lifecycleOwner.lifecycleScope.launch {
                                        val myResult =
                                            getAllMovieee(myDao = myDao, lifecycleOwner)
                                        moviesState.value = myResult
                                    }

                                    ShowToastMessage("Movie deleted", lifecycleOwner, context)
                                } else {
                                    // todo
                                }
                            }
                        }
                    )
                }
                item(span = { GridItemSpan(1) }) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                    }
                }
            }
        }

    }

}

fun filterItems(searchQuery: String, commonlist: List<Movies>): List<Movies> {
    return commonlist.filter { item ->
        item.title.contains(searchQuery, ignoreCase = true)
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onTextChange: (String) -> Unit,
    onClearText: () -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = { onTextChange(it) },
        placeholder = {
            Text(
                "New movies", color = Color.Black,
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraBold,
                fontSize =
                20.sp
            )
        },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (!searchQuery.equals("")) {
                IconButton(
                    onClick = {
                        onTextChange("")
                        onClearText
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },

        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 0.dp),
        textStyle = TextStyle(
            color = Color.Black, fontSize = 18.sp,
        ),

        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            cursorColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            backgroundColor = SearchbarColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

fun deleteMovieItem(
    movie: Movies,
    myDao: MovieDao,
    lifecycleOwner: LifecycleOwner,
    callback: (Boolean) -> Unit
) {
    val coroutineScope = lifecycleOwner.lifecycleScope
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            try {
                myDao.deleteMovie(movie)
                callback(true)
            } catch (
                e: Exception
            ) {
                callback(false)
            }
        }
    }
}


suspend fun getAllMovieee(myDao: MovieDao, lifecycleOwner: LifecycleOwner): List<Movies> {
    val coroutineScope = lifecycleOwner.lifecycleScope
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            myDao.getAllMovies()
        }
    }
    return myDao.getAllMovies()
}

fun ShowToastMessage(message: String, lifecycleOwner: LifecycleOwner, context: Context) {
    val coroutineScope = lifecycleOwner.lifecycleScope
    coroutineScope.launch {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

