package com.onirutla.musicwalker.ui.screens.detail

sealed interface DetailMusicScreenEvent {
    data object ResumeMusic : DetailMusicScreenEvent
    data object PauseMusic : DetailMusicScreenEvent
    data object SkipNext : DetailMusicScreenEvent
    data object SkipPrevious : DetailMusicScreenEvent
    data class OnShuffleChange(val isShuffle: Boolean) : DetailMusicScreenEvent
    data class OnRepeatOneChange(val isRepeatOne: Boolean) : DetailMusicScreenEvent
    data class OnPositionChange(val position: Long) : DetailMusicScreenEvent
}
