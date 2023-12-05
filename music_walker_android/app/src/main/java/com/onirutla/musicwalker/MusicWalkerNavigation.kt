package com.onirutla.musicwalker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.onirutla.musicwalker.ui.screens.Screens
import com.onirutla.musicwalker.ui.screens.main.MainScreen

@Composable
fun MusicWalkerNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.MainScreen.route
    ) {
        composable(
            route = Screens.MainScreen.route,
            arguments = listOf(),
            deepLinks = listOf()
        ) {
            MainScreen()
        }
    }
}
