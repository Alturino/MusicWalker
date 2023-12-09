package com.onirutla.musicwalker.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onirutla.musicwalker.core.media.playback_controller.PlaybackController
import com.onirutla.musicwalker.domain.repository.MusicRepository
import com.onirutla.musicwalker.ui.screens.MusicPlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val playbackController: PlaybackController,
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val musics = musicRepository.getMusics()
            playbackController.addMediaItems(musics)
            _state.update { it.copy(musics = musics, isLoading = false) }
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnMusicClicked -> {
                _state.update { it.copy(selectedMusic = event.music) }
            }

            MainScreenEvent.OnPlayMusic -> {
                _state.value.musics.indexOf(_state.value.selectedMusic).let {
                    playbackController.play(it)
                }
            }

            MainScreenEvent.OnPauseMusic -> {
                playbackController.pause()
            }

            MainScreenEvent.OnResumeMusic -> {
                playbackController.resume()
            }
        }
    }
}
