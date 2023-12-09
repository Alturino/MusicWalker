package com.onirutla.musicwalker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.onirutla.musicwalker.ui.screens.Screens
import com.onirutla.musicwalker.ui.screens.SharedViewModel
import com.onirutla.musicwalker.ui.screens.detail.DetailMusicScreen
import com.onirutla.musicwalker.ui.screens.main.MainScreen
import com.onirutla.musicwalker.ui.screens.main.MainScreenViewModel
import timber.log.Timber

@Composable
fun MusicWalkerNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val musicPlaybackUiState by sharedViewModel.state.collectAsState()

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
            val vm: MainScreenViewModel = hiltViewModel()
            val state by vm.state.collectAsState()
            MainScreen(
                onEvent = vm::onEvent,
                onUiEvent = {},
                playbackUiState = musicPlaybackUiState,
                state = state,
            )
        }
        composable(
            route = Screens.DetailMusicScreen.route,
            arguments = listOf(),
            deepLinks = listOf()
        ) {
            DetailMusicScreen()
        }
    }
}
