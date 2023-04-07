package com.projects.moviesapp.android.home


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
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.moviesapp.android.Red
import com.projects.moviesapp.android.favourite.FavouriteViewModel
import com.projects.moviesapp.domain.model.MainMovie

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenState,
    loadNextMovies: (Boolean) -> Unit,
    navigateToDetail: (MainMovie) -> Unit,
    viewModel: HomeViewModel,
    favouriteViewModel: FavouriteViewModel,
    //roomViewModel: MoviesViewModel
) {


    val keyboardController = LocalSoftwareKeyboardController.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.refreshing,
        onRefresh = { loadNextMovies(true) })


    viewModel.myListLiveData.observeForever {
    }

    val listAll = viewModel.viewModelAllList
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        var searchQuery by remember { mutableStateOf("") }
        SearchBar(searchQuery,
            onTextChange = { query ->
                searchQuery = query
                uiState.movies = filterItems(searchQuery, listAll)
            },
            onClearText = {
                // Handle onClearText event
                keyboardController?.hide()
                searchQuery = ""
                uiState.movies = filterItems(searchQuery, listAll)
            }
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                .pullRefresh(state = pullRefreshState)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(
                    uiState.movies,
                    key = { _, movie -> movie.id }
                ) { index, movie ->
                    MovieListItem(
                        movie = movie,
                        onMovieClick = { navigateToDetail(movie) },
                        viewModel = viewModel,
                        //favouriteViewModel = favouriteViewModel,
                     //   roomViewModel = roomViewModel

                    )
                    if (index >= uiState.movies.size - 1 && !uiState.loading && !uiState.loadFinished) {
                        LaunchedEffect(key1 = Unit, block = { loadNextMovies(false) })
                    }
                }
                if (uiState.loading && uiState.movies.isNotEmpty()) {
                    item(span = { GridItemSpan(1) }) {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                color = Red
                            )
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.refreshing,
                state = pullRefreshState,
                modifier = modifier.align(Alignment.TopCenter)

            )
        }


    }

}

fun filterItems(searchQuery: String, commonlist: List<MainMovie>): List<MainMovie> {
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
    Surface(
        shape = RoundedCornerShape(20.dp),
    ) {
    }
    TextField(
        value = searchQuery,
        onValueChange = { onTextChange(it) },
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (!searchQuery.equals("")) {
                IconButton(
                    onClick = {
                        onTextChange("")
                        onClearText
                        // Remove text from TextField when you press the 'X' icon
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
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 0.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}



