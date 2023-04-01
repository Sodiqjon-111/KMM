package com.projects.moviesapp.android

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.projects.moviesapp.android.common.Detail
import com.projects.moviesapp.android.home.Home2
import com.projects.moviesapp.android.home.HomeScreen
import com.projects.moviesapp.android.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val textState = remember { mutableStateOf(TextFieldValue("")) }
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
    object Settings : Screens("settings_screen")
    object About : Screens("about_screen")
}


@Composable
fun NavigationSetup(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            val homeViewModel: HomeViewModel = koinViewModel()
            Home2(
                uiState = homeViewModel.uiState,
                loadNextMovies = {
                    homeViewModel.loadMovies(forceReload = it)
                },
                navigateToDetail = {
                    navController.navigate(
                        "${Detail.route}/${it.id}"
                    )
                }
            )
        }
        composable(BottomNavItem.Settings.route) {
            //  SettingsScreen(navController)
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
        route = Screens.Settings.route,
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
