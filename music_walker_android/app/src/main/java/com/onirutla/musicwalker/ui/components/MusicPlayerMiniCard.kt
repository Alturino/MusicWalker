package com.onirutla.musicwalker.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.onirutla.musicwalker.core.media.playback_controller.PlayerState
import com.onirutla.musicwalker.domain.models.Music
import com.onirutla.musicwalker.domain.models.dummyMusic
import com.onirutla.musicwalker.ui.theme.MusicWalkerTheme

@Composable
fun MusicMiniPlayerCard(
    modifier: Modifier = Modifier,
    music: Music?,
    playerState: PlayerState,
    onResumeClicked: () -> Unit,
    onPauseClicked: () -> Unit,
) {
    ListItem(
        modifier = modifier.clip(MaterialTheme.shapes.small),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        headlineContent = {
            Text(
                text = music?.title.orEmpty(),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )
        },
        supportingContent = {
            Text(
                text = music?.artist.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingContent = {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(45.dp)
                    .clip(MaterialTheme.shapes.small),
                model = music?.image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                loading = { CircularProgressIndicator() },
            )
        },
        trailingContent = {
            IconButton(
                onClick = {
                    when (playerState) {
                        PlayerState.PLAYING -> {
                            onPauseClicked()
                        }

                        PlayerState.PAUSED -> {
                            onResumeClicked()
                        }

                        PlayerState.STOPPED -> {}
                    }
                }
            ) {
                Icon(
                    imageVector = if (playerState == PlayerState.PLAYING) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
fun MusicPlayerMiniCardPreview() {
    MusicWalkerTheme {
        MusicMiniPlayerCard(
            music = dummyMusic[0],
            playerState = PlayerState.STOPPED,
            onResumeClicked = {},
            onPauseClicked = {},
        )
    }
}
