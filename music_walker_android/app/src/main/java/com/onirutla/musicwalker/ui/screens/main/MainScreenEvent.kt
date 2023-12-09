package com.onirutla.musicwalker.ui.screens.main

import com.onirutla.musicwalker.domain.models.Music

sealed interface MainScreenEvent {
    data class OnMusicClicked(val music: Music) : MainScreenEvent
    data object OnPlayMusic : MainScreenEvent
    data object OnPauseMusic : MainScreenEvent
    data object OnResumeMusic : MainScreenEvent
}

sealed interface MainScreenUiEvent {
    data object OnNavigateToMusicPlayer : MainScreenUiEvent
}
