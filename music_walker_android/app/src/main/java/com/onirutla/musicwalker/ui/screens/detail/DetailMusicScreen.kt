package com.onirutla.musicwalker.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.onirutla.musicwalker.R
import com.onirutla.musicwalker.core.media.playback_controller.PlayerState
import com.onirutla.musicwalker.domain.models.dummyMusic
import com.onirutla.musicwalker.ui.screens.MusicPlayerUiState
import com.onirutla.musicwalker.ui.theme.MusicWalkerTheme
import com.onirutla.musicwalker.ui.util.toStringTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMusicScreen(
    modifier: Modifier = Modifier,
    state: MusicPlayerUiState,
    onEvent: (DetailMusicScreenEvent) -> Unit,
    onUiEvent: (DetailMusicScreenUiEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = { onUiEvent(DetailMusicScreenUiEvent.OnNavigateUp) }) {
                        Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null)
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(
            left = 16.dp,
            top = 16.dp,
            right = 16.dp,
            bottom = 16.dp
        ),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(250.dp)
                        .clip(MaterialTheme.shapes.large),
                    model = state.currentMusic?.image,
                    contentDescription = null,
                    loading = { CircularProgressIndicator() }
                )
                Text(
                    text = state.currentMusic?.title.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = state.currentMusic?.artist.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Slider(
                    value = state.currentPosition.toFloat(),
                    valueRange = 0f..state.totalDuration.toFloat(),
                    onValueChange = { onEvent(DetailMusicScreenEvent.OnPositionChange(it.toLong())) }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = state.currentPosition.toStringTime(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = state.totalDuration.toStringTime(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            if (state.isShuffleEnabled) {
                                onEvent(DetailMusicScreenEvent.OnShuffleChange(false))
                            } else {
                                onEvent(DetailMusicScreenEvent.OnShuffleChange(true))
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Shuffle, contentDescription = null)
                    }
                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { onEvent(DetailMusicScreenEvent.SkipPrevious) }
                    ) {
                        Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = null)
                    }
                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            if (state.playerState == PlayerState.PLAYING) {
                                onEvent(DetailMusicScreenEvent.PauseMusic)
                            } else {
                                onEvent(DetailMusicScreenEvent.ResumeMusic)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (state.playerState == PlayerState.PLAYING) Icons.Default.PauseCircleFilled else Icons.Default.PlayCircleFilled,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { onEvent(DetailMusicScreenEvent.SkipNext) }
                    ) {
                        Icon(imageVector = Icons.Default.SkipNext, contentDescription = null)
                    }
                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            if (state.isRepeatOneEnabled) {
                                onEvent(DetailMusicScreenEvent.OnRepeatOneChange(false))
                            } else {
                                onEvent(DetailMusicScreenEvent.OnRepeatOneChange(true))
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.RepeatOne, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailMusicScreenPreview() {
    MusicWalkerTheme {
        DetailMusicScreen(
            state = MusicPlayerUiState(
                playerState = PlayerState.STOPPED,
                currentMusic = dummyMusic[0],
                currentPosition = 0L,
                totalDuration = 90070,
                isShuffleEnabled = false
            ),
            onEvent = {},
            onUiEvent = {}
        )
    }
}
