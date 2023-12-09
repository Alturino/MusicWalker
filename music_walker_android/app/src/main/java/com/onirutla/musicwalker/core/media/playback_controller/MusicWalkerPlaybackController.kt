package com.onirutla.musicwalker.core.media.playback_controller

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.onirutla.musicwalker.domain.models.Music
import com.onirutla.musicwalker.domain.models.toMediaItems
import com.onirutla.musicwalker.domain.models.toMusic
import com.onirutla.musicwalker.ui.screens.MusicPlaybackUiState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
class MusicWalkerPlaybackController @Inject constructor(
    private val mediaControllerFuture: ListenableFuture<MediaController>,
) : PlaybackController {

    private val mediaController get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

    override val musicPlaybackUiState: Flow<MusicPlaybackUiState> = callbackFlow {
        val mediaControllerListener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                with(player) {
                    trySend(
                        MusicPlaybackUiState(
                            playbackState.toPlayerState(isPlaying = isPlaying),
                            currentMediaItem?.toMusic(),
                            currentPosition.coerceAtLeast(0L),
                            duration.coerceAtLeast(0L),
                            shuffleModeEnabled,
                            repeatMode == Player.REPEAT_MODE_ONE
                        )
                    )
                }
            }
        }

        mediaControllerFuture.addListener({
            mediaController?.addListener(mediaControllerListener)
        }, MoreExecutors.directExecutor())

        awaitClose { mediaController?.removeListener(mediaControllerListener) }
    }.onEach { Timber.d("$it") }
        .catch { Timber.e(it) }

    fun Int.toPlayerState(isPlaying: Boolean) = when (this) {
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

    override fun getCurrentPosition(): Long = mediaController?.currentPosition ?: 0

}
