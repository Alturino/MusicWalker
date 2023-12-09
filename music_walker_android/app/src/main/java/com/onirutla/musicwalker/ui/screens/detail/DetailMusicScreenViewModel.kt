package com.onirutla.musicwalker.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onirutla.musicwalker.core.media.playback_controller.PlaybackController
import com.onirutla.musicwalker.ui.screens.MusicPlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailMusicScreenViewModel @Inject constructor(
    private val playbackController: PlaybackController,
) : ViewModel() {

    fun onEvent(event: DetailMusicScreenEvent) {
        when (event) {
            is DetailMusicScreenEvent.OnPositionChange -> {
                playbackController.seekTo(event.position)
            }

            is DetailMusicScreenEvent.OnRepeatOneChange -> {
                playbackController.setRepeatOneEnabled(event.isRepeatOne)
            }

            is DetailMusicScreenEvent.OnShuffleChange -> {
                playbackController.setShuffleModeEnabled(event.isShuffle)
            }

            DetailMusicScreenEvent.PauseMusic -> {
                playbackController.pause()
            }

            DetailMusicScreenEvent.ResumeMusic -> {
                playbackController.resume()
            }

            DetailMusicScreenEvent.SkipNext -> {
                playbackController.seekToNext()
            }

            DetailMusicScreenEvent.SkipPrevious -> {
                playbackController.seekToPrevious()
            }
        }
    }

}
