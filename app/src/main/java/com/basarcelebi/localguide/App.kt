package com.basarcelebi.localguide

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.basarcelebi.localguide.network.UserAuth
import com.basarcelebi.localguide.screens.ContributionScreen
import com.basarcelebi.localguide.screens.DetailsCard
import com.basarcelebi.localguide.screens.DetailsScreen
import com.basarcelebi.localguide.screens.FaqScreen
import com.basarcelebi.localguide.screens.FavoriteScreen
import com.basarcelebi.localguide.screens.HomeScreen
import com.basarcelebi.localguide.screens.ProfileDetails
import com.basarcelebi.localguide.screens.ProfileScreen
import com.basarcelebi.localguide.screens.SettingsScreen
import com.basarcelebi.localguide.viewmodel.FavoritesViewModel

@Composable
fun LocalGuideApp(innerPadding: PaddingValues, navController: NavHostController = rememberNavController()) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isBackStackEmpty by remember(navBackStackEntry) {
        mutableStateOf(navBackStackEntry?.destination?.route == "home")
    }
    Scaffold(
        topBar = { TopNavigationBar(navController, isBackStackEmpty) },
        bottomBar = { BottomNavigationBar(navController) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationHost(navController, innerPadding)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavController, isBackStackEmpty: Boolean) {

    val navigationIcon = @Composable {
        if (!isBackStackEmpty) {
            IconButton(onClick = { navController.popBackStack() },
                modifier = Modifier.size(50.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }

    CenterAlignedTopAppBar(
        title = { Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.size(50.dp),alignment = Alignment.Center) },
        navigationIcon = navigationIcon)
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute = navController.currentDestination?.route

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        val items = listOf("home", "favorites", "profile")
        items.forEach { item ->
            val isSelected = currentRoute == item
            BottomNavigationItem(
                selected = isSelected,
                modifier = Modifier.padding(4.dp),
                icon = {
                    when (item) {
                        "favorites" -> if (isSelected) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        "home" -> if (isSelected) {
                            Icon(
                                Icons.Filled.Home,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Home,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        "profile" -> if (isSelected) {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                label = {
                    Text(
                        text = item.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            fontSize = if (isSelected) 16.sp else 14.sp
                        ),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                onClick = {
                    navController.navigate(item) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}


@Composable
fun NavigationHost(navController: NavHostController, innerPadding: PaddingValues) {
    val viewModel: FavoritesViewModel = viewModel()
    NavHost(navController = navController, startDestination = "home") {
        composable("home",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) { HomeScreen(UserAuth(),viewModel, navController) }
        composable("favorites",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) { FavoriteScreen(navController,viewModel) }
        composable("profile",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) { ProfileScreen(UserAuth(),navController) }
        composable("details/{placeName}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }, arguments = listOf(navArgument("placeName") { type = NavType.StringType })) { backStackEntry ->
            val placeName = backStackEntry.arguments?.getString("placeName")
            val place = viewModel.places.value?.find { it.name == placeName }
            place?.let {
                DetailsScreen(navController, it)
            }
        }
        composable("contributions",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) { ContributionScreen() }
        composable("settings",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) { SettingsScreen() }
        composable("faq",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) { FaqScreen() }
        composable("details",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) { ProfileDetails(navController) }
    }
}

@Composable
fun LocalGuideAppThemePreview(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun LocalGuideAppPreview() {
    LocalGuideAppThemePreview {
        LocalGuideApp(PaddingValues(),rememberNavController())
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar(rememberNavController())
}


