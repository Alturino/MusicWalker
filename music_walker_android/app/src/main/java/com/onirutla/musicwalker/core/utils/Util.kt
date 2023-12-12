package com.onirutla.musicwalker.core.utils

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.onirutla.musicwalker.domain.models.Music

fun MediaItem.toMusic() = Music(
    id = mediaId,
    source = mediaId,
    title = mediaMetadata.title.toString(),
    artist = mediaMetadata.artist.toString(),
    genre = mediaMetadata.genre.toString(),
    album = mediaMetadata.albumTitle.toString(),
    image = mediaMetadata.artworkUri.toString(),
    trackNumber = mediaMetadata.trackNumber ?: 0,
    totalTrackCount = mediaMetadata.totalTrackCount ?: 0,
)

fun List<MediaItem>.toMusics() = map { it.toMusic() }

fun Music.toMediaItem() = MediaItem.Builder()
    .setMediaId(source)
    .setUri(source)
    .setMediaMetadata(
        MediaMetadata.Builder()
            .setTitle(title)
            .setArtist(artist)
            .setGenre(genre)
            .setAlbumTitle(album)
            .setArtworkUri(image.toUri())
            .setTrackNumber(trackNumber)
            .setTotalTrackCount(totalTrackCount)
            .build()
    )
    .build()

fun List<Music>.toMediaItems() = map { it.toMediaItem() }
