package com.onirutla.musicwalker.domain.models

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata

data class Music(
    val album: String = "",
    val artist: String = "",
    val duration: Int = 0,
    val genre: String = "",
    val id: String = "",
    val image: String = "",
    val site: String = "",
    val source: String = "",
    val title: String = "",
    val totalTrackCount: Int = 0,
    val trackNumber: Int = 0,
)

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
