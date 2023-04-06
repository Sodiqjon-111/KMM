package com.projects.moviesapp.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.projects.moviesapp.android.common.Detail
import com.projects.moviesapp.android.detail.DetailScreen
import com.projects.moviesapp.android.detail.DetailViewModel
import com.projects.moviesapp.android.favourite.FavouriteScreen
import com.projects.moviesapp.android.favourite.FavouriteViewModel
import com.projects.moviesapp.android.home.HomeScreen
import com.projects.moviesapp.android.home.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {

        }
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //  val textState = remember { mutableStateOf(TextFieldValue("")) }
                    Column {

                        //SearchViewMine(state = textState);
                        val navController = rememberNavController()
                        Scaffold(
                            bottomBar = { BottomNavigationBar(navController = navController) }
                        ) {
                            NavigationSetup(navController = navController)
                        }
                    }


                }
            }
        }
    }
}

sealed class Screens(val route: String) {
    object Home : Screens("home_screen")
    object Favourite : Screens("settings_screen")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavigationSetup(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            val homeViewModel: HomeViewModel = koinViewModel()
           // val roomViewModel: MoviesViewModel = koinViewModel()
            HomeScreen(
                uiState = homeViewModel.uiState,
                loadNextMovies = {
                    homeViewModel.loadMovies(forceReload = it)
                },
                navigateToDetail = {
                    navController.navigate(
                        "${Detail.route}/${it.id}"
                    )
                },
                viewModel = homeViewModel,
                favouriteViewModel = FavouriteViewModel(),
               // roomViewModel = roomViewModel
            )
        }
        composable(BottomNavItem.Settings.route) {
            val homeViewModel: HomeViewModel = koinViewModel()
           // val roomViewModel: MoviesViewModel = koinViewModel()

            FavouriteScreen(
                uiState = homeViewModel.uiState,
//                  loadNextMovies = {
//                     // homeViewModel.loadMovies(forceReload = it)
//                  },
                navigateToDetail = {
                    navController.navigate(
                        "${Detail.route}/${it.id}"
                    )
                },
                viewModel = homeViewModel,
                favouriteViewModel = FavouriteViewModel(),
               // roomViewModel = roomViewModel
            )
        }
        composable(Detail.routeWithArgs, arguments = Detail.arguments) {
            val movieId = it.arguments?.getInt("movieId") ?: "0"
            val detailViewModel: DetailViewModel = koinViewModel(
                parameters = { parametersOf(movieId) }
            )

            DetailScreen(uiState = detailViewModel.uiState, navController = navController)
        }

    }
}

sealed class BottomNavItem(
    val route: String,
    val titleRes: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = Screens.Home.route,
        titleRes = "Home",
        icon = Icons.Default.Home
    )

    object Settings : BottomNavItem(
        route = Screens.Favourite.route,
        titleRes = "Favourites",
        icon = Icons.Default.Favorite
    )
}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Settings
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.titleRes
                    )
                },
                label = { Text(text = item.titleRes) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
