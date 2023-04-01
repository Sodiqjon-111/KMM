package com.projects.moviesapp.android.home

import SearchViewMine
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projects.moviesapp.android.Red
import com.projects.moviesapp.domain.model.Movie


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home2(
    modifier: Modifier = Modifier,
    uiState: HomeScreenState,
    loadNextMovies: (Boolean) -> Unit,
    navigateToDetail: (Movie) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.refreshing,
        onRefresh = { loadNextMovies(true) })
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    var listAll= uiState.movies
    var Big= uiState.movies

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
       // SearchViewMine(items = items, modifier = Modifier.fillMaxSize())
        //SearchableGrid(uiState.movies, modifier = Modifier.fillMaxSize())
        var searchQuery by remember { mutableStateOf("") }
        SearchBar(searchQuery, onTextChange = { query ->
            searchQuery = query
            Log.d(TAG, "________________________________********************")
            listAll= filterItems(query,uiState.movies)

        })

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                .pullRefresh(state = pullRefreshState)
        ) {
            LazyGrid(uiState = HomeScreenState(),  )

//            LazyVerticalGrid(
//                columns = GridCells.Fixed(1),
//                contentPadding = PaddingValues(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                itemsIndexed(
//                    listAll,
//                    key = { _, movie -> movie.id }
//                ) { index, movie ->
//                    MovieListItem(movie = movie, onMovieClick = { navigateToDetail(movie) })
//
//                    if (index >= uiState.movies.size - 1 && !uiState.loading && !uiState.loadFinished) {
//                        LaunchedEffect(key1 = Unit, block = { loadNextMovies(false) })
//                    }
//                }
//
//
//                if (uiState.loading && uiState.movies.isNotEmpty()) {
//                    item(span = { GridItemSpan(1) }) {
//                        Row(
//                            modifier = modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            CircularProgressIndicator(
//                                color = Red
//                            )
//                        }
//                    }
//                }
//            }

            PullRefreshIndicator(
                refreshing = uiState.refreshing,
                state = pullRefreshState,
                modifier = modifier.align(Alignment.TopCenter)

            )
        }

    }



}

fun filterItems(searchQuery: String,commonlist:List<Movie>): List<Movie> {
    return commonlist.filter { item ->
        item.title.contains(searchQuery, ignoreCase = true)
    }
}

@Composable
fun LazyGrid(
    uiState: HomeScreenState,
//    items: List<Movie>,
//    onItemSelected: (Movie) -> Unit,
    modifier: Modifier = Modifier

) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            uiState.movies,
            key = { _, movie -> movie.id }
        ) { index, movie ->
//            MovieListItem(movie = movie, onMovieClick = { navigateToDetail(movie) })
//
//            if (index >= uiState.movies.size - 1 && !uiState.loading && !uiState.loadFinished) {
//                LaunchedEffect(key1 = Unit, block = { loadNextMovies(false) })
//            }
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

}
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar("", onTextChange = {})
}
@Composable
fun SearchBar(
    searchQuery: String,
    onTextChange: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = { onTextChange(it) },
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

//
//@Composable
//fun SearchBar(
//    onQueryChanged: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var query by remember { mutableStateOf("") }
//
//    TextField(
//        value = query,
//        onValueChange = {
//            query = it
//            onQueryChanged(it)
//        },
//        label = { Text("Search") },
//        singleLine = true,
//        modifier = modifier
//    )
//}
//
//@Composable
//fun FilterableGrid(
//    items: List<Movie>,
//    modifier: Modifier = Modifier
//) {
//
//
////    LazyGrid(
////        items = filteredItems,
////        onItemSelected = {},
////        modifier = modifier
////    )
//}
//
//@Composable
//fun SearchableGrid(
//    items: List<Movie>,
//    modifier: Modifier = Modifier
//
//) {
//    Column(modifier) {
//        var filteredItems by remember { mutableStateOf(items) }
//        SearchBar(onQueryChanged = { query ->
//            filteredItems = if (query.isEmpty()) {
//                items
//            } else {
//                items.filter { it.title.contains(query, ignoreCase = true) }
//            }
//        })
//
//        FilterableGrid(items = filteredItems)
//    }
//}
