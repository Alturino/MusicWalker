package com.onirutla.musicwalker.core.media.playback_controller

import com.onirutla.musicwalker.domain.models.Music
import com.onirutla.musicwalker.ui.screens.MusicPlaybackUiState
import kotlinx.coroutines.flow.Flow

interface PlaybackController {

    fun addMediaItems(musics: List<Music>)

    fun play(mediaItemIndex: Int)

    fun resume()

    fun pause()

    fun seekTo(position: Long)

    fun seekToNext()

    fun seekToPrevious()

    fun setShuffleModeEnabled(isEnabled: Boolean)

    fun setRepeatOneEnabled(isEnabled: Boolean)

    fun getCurrentPosition(): Long

    val musicPlaybackUiState: Flow<MusicPlaybackUiState>
}
