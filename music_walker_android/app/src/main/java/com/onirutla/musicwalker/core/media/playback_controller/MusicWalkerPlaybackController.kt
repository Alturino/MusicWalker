package com.onirutla.musicwalker.core.media.playback_controller

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.onirutla.musicwalker.core.utils.toMediaItems
import com.onirutla.musicwalker.core.utils.toMusic
import com.onirutla.musicwalker.di.ApplicationMainCoroutineScope
import com.onirutla.musicwalker.domain.models.Music
import com.onirutla.musicwalker.ui.screens.MusicPlayerUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class MusicWalkerPlaybackController @Inject constructor(
    @ApplicationMainCoroutineScope private val applicationMainCoroutineScope: CoroutineScope,
    private val mediaControllerFuture: ListenableFuture<MediaController>,
) : PlaybackController {

    private lateinit var mediaController: MediaController

    private val _playerUiState = MutableStateFlow(MusicPlayerUiState())
    override val playerUiState: StateFlow<MusicPlayerUiState> = _playerUiState.asStateFlow()

    private val position = _playerUiState.mapLatest { it.playerState }
        .distinctUntilChanged()
        .flatMapLatest {
            flow {
                while (it == PlayerState.PLAYING) {
                    emit(mediaController.currentPosition)
                    delay(200.milliseconds)
                }
            }
        }
        .onEach { position -> _playerUiState.update { it.copy(currentPosition = position) } }

    init {
        applicationMainCoroutineScope.launch {
            mediaController = mediaControllerFuture.await()
            val listener = object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    _playerUiState.update {
                        with(player) {
                            it.copy(
                                playerState = playbackState.toPlayerState(isPlaying = isPlaying),
                                currentMusic = currentMediaItem?.toMusic(),
                                currentPosition = currentPosition.coerceAtLeast(0L),
                                totalDuration = duration.coerceAtLeast(0L),
                                isShuffleEnabled = shuffleModeEnabled,
                                isRepeatOneEnabled = repeatMode == Player.REPEAT_MODE_ONE
                            )
                        }
                    }
                }
            }
            mediaController.addListener(listener)
        }
        position.launchIn(applicationMainCoroutineScope)
    }

    private fun Int.toPlayerState(isPlaying: Boolean) = when (this) {
        Player.STATE_IDLE -> PlayerState.STOPPED
        Player.STATE_ENDED -> PlayerState.STOPPED
        else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
    }

    override fun addMediaItems(musics: List<Music>) {
        mediaController.addMediaItems(musics.toMediaItems())
    }

    override fun play(mediaItemIndex: Int) {
        mediaController.apply {
            seekToDefaultPosition(mediaItemIndex)
            playWhenReady = true
            prepare()
        }
    }

    override fun resume() {
        mediaController.play()
    }

    override fun pause() {
        mediaController.pause()
    }

    override fun seekTo(position: Long) {
        mediaController.seekTo(position)
    }

    override fun seekToNext() {
        mediaController.seekToNext()
    }

    override fun seekToPrevious() {
        mediaController.seekToPrevious()
    }

    override fun setShuffleModeEnabled(isEnabled: Boolean) {
        mediaController.shuffleModeEnabled = isEnabled
    }

    override fun setRepeatOneEnabled(isEnabled: Boolean) {
        mediaController.repeatMode = if (isEnabled) {
            Player.REPEAT_MODE_ONE
        } else {
            Player.REPEAT_MODE_OFF
        }
    }

}
