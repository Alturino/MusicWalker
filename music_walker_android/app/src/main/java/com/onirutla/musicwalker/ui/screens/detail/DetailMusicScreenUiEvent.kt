package com.onirutla.musicwalker.ui.screens.detail

sealed interface DetailMusicScreenUiEvent {
    data object OnNavigateUp : DetailMusicScreenUiEvent
}
