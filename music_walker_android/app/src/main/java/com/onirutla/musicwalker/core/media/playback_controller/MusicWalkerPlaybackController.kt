package com.onirutla.musicwalker.core.media.playback_controller

import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.onirutla.musicwalker.core.utils.toMediaItems
import com.onirutla.musicwalker.core.utils.toMusic
import com.onirutla.musicwalker.di.ApplicationMainCoroutineScope
import com.onirutla.musicwalker.di.DispatchersIO
import com.onirutla.musicwalker.di.DispatchersMain
import com.onirutla.musicwalker.domain.models.Music
import com.onirutla.musicwalker.ui.screens.MusicPlayerUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class MusicWalkerPlaybackController @Inject constructor(
    @ApplicationMainCoroutineScope private val applicationMainCoroutineScope: CoroutineScope,
    @DispatchersMain private val dispatchersMain: CoroutineDispatcher,
    @DispatchersIO private val dispatchersIO: CoroutineDispatcher,
    private val mediaControllerFuture: ListenableFuture<MediaController>,
) : PlaybackController {

    private val mediaController
        get() = if (mediaControllerFuture.isDone) {
            mediaControllerFuture.get()
        } else {
            null
        }

    override val playerUiState: Flow<MusicPlayerUiState> = callbackFlow {
        val mediaControllerListener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                if (player.isPlaying) {
                    applicationMainCoroutineScope.launch {
                        while (player.isPlaying) {
                            delay(1.seconds)
                            trySend(
                                MusicPlayerUiState(
                                    playerState = player.playbackState.toPlayerState(isPlaying = player.isPlaying),
                                    currentMusic = player.currentMediaItem?.toMusic(),
                                    currentPosition = player.currentPosition.coerceAtLeast(0L),
                                    totalDuration = player.duration.coerceAtLeast(0L),
                                    isShuffleEnabled = player.shuffleModeEnabled,
                                    isRepeatOneEnabled = player.repeatMode == Player.REPEAT_MODE_ONE
                                )
                            ).onFailure { Timber.e(it, "Failed to send with cause: $it") }
                                .onClosed { Timber.e(it, "Closed with cause: $it") }
                                .onSuccess { Timber.d("Success sent") }
                        }
                    }
                }
                if (!player.isPlaying) {
                    trySend(
                        MusicPlayerUiState(
                            playerState = player.playbackState.toPlayerState(isPlaying = player.isPlaying),
                            currentMusic = player.currentMediaItem?.toMusic(),
                            currentPosition = player.currentPosition.coerceAtLeast(0L),
                            totalDuration = player.duration.coerceAtLeast(0L),
                            isShuffleEnabled = player.shuffleModeEnabled,
                            isRepeatOneEnabled = player.repeatMode == Player.REPEAT_MODE_ONE
                        )
                    )
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Timber.e(error)
                close(error)
            }

            override fun onPlayerErrorChanged(error: PlaybackException?) {
                super.onPlayerErrorChanged(error)
                Timber.e(error)
                close(error)
            }
        }
        mediaControllerFuture.addListener(
            { mediaController?.addListener(mediaControllerListener) },
            dispatchersMain.asExecutor()
        )
        awaitClose {
            mediaController?.removeListener(mediaControllerListener)
            close()
        }
    }.conflate()
        .flowOn(dispatchersIO)
        .onEach { Timber.d("$it") }
        .catch { Timber.e(it) }

    private fun Int.toPlayerState(isPlaying: Boolean) = when (this) {
        Player.STATE_IDLE -> PlayerState.STOPPED
        Player.STATE_ENDED -> PlayerState.STOPPED
        else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
    }

    override fun addMediaItems(musics: List<Music>) {
        mediaController?.addMediaItems(musics.toMediaItems())
    }

    override fun play(mediaItemIndex: Int) {
        mediaController?.apply {
            seekToDefaultPosition(mediaItemIndex)
            playWhenReady = true
            prepare()
        }
    }

    override fun resume() {
        mediaController?.play()
    }

    override fun pause() {
        mediaController?.pause()
    }

    override fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    override fun seekToNext() {
        mediaController?.seekToNext()
    }

    override fun seekToPrevious() {
        mediaController?.seekToPrevious()
    }

    override fun setShuffleModeEnabled(isEnabled: Boolean) {
        mediaController?.shuffleModeEnabled = isEnabled
    }

    override fun setRepeatOneEnabled(isEnabled: Boolean) {
        mediaController?.repeatMode = if (isEnabled) {
            Player.REPEAT_MODE_ONE
        } else {
            Player.REPEAT_MODE_OFF
        }
    }

}
