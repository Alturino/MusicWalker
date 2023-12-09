package com.onirutla.musicwalker.ui.screens

import com.onirutla.musicwalker.core.media.playback_controller.PlayerState
import com.onirutla.musicwalker.domain.models.Music

data class MusicPlaybackUiState(
    val playerState: PlayerState = PlayerState.STOPPED,
    val currentMusic: Music? = Music(),
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false,
)
