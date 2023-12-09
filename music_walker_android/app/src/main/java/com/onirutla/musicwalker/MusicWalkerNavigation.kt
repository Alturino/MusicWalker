package com.onirutla.musicwalker

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.onirutla.musicwalker.ui.screens.Screens
import com.onirutla.musicwalker.ui.screens.SharedViewModel
import com.onirutla.musicwalker.ui.screens.detail.DetailMusicScreen
import com.onirutla.musicwalker.ui.screens.detail.DetailMusicScreenUiEvent
import com.onirutla.musicwalker.ui.screens.detail.DetailMusicScreenViewModel
import com.onirutla.musicwalker.ui.screens.main.MainScreen
import com.onirutla.musicwalker.ui.screens.main.MainScreenUiEvent
import com.onirutla.musicwalker.ui.screens.main.MainScreenViewModel

@Composable
fun MusicWalkerNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val playbackUiState by sharedViewModel.state.collectAsState()

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
                onUiEvent = {
                    when (it) {
                        MainScreenUiEvent.OnNavigateToMusicPlayer -> {
                            navController.navigate(route = Screens.DetailMusicScreen.route)
                        }
                    }
                },
                playbackUiState = playbackUiState,
                state = state,
            )
        }
        composable(
            route = Screens.DetailMusicScreen.route,
            enterTransition = {
                expandVertically(animationSpec = tween(300), expandFrom = Alignment.Top)
            },
            exitTransition = {
                shrinkVertically(animationSpec = tween(300), shrinkTowards = Alignment.Bottom)
            },
            arguments = listOf(),
            deepLinks = listOf()
        ) {
            val vm: DetailMusicScreenViewModel = hiltViewModel()
            DetailMusicScreen(
                state = playbackUiState,
                onEvent = vm::onEvent,
                onUiEvent = {
                    when (it) {
                        DetailMusicScreenUiEvent.OnNavigateUp -> {
                            navController.navigateUp()
                        }
                    }
                }
            )
        }
    }
}
