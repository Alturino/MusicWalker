package com.onirutla.musicwalker.core.data.remote.models

import com.onirutla.musicwalker.domain.models.Music
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    @SerialName("album")
    val album: String? = "",
    @SerialName("artist")
    val artist: String? = "",
    @SerialName("duration")
    val duration: Int? = 0,
    @SerialName("genre")
    val genre: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("image")
    val image: String? = "",
    @SerialName("site")
    val site: String? = "",
    @SerialName("source")
    val source: String? = "",
    @SerialName("title")
    val title: String? = "",
    @SerialName("totalTrackCount")
    val totalTrackCount: Int? = 0,
    @SerialName("trackNumber")
    val trackNumber: Int? = 0,
)

fun MusicResponse.toMusic() = Music(
    album = album.orEmpty(),
    artist = artist.orEmpty(),
    duration = duration ?: 0,
    genre = genre.orEmpty(),
    id = id.orEmpty(),
    image = image.orEmpty(),
    site = site.orEmpty(),
    source = source.orEmpty(),
    title = title.orEmpty(),
    totalTrackCount = 0,
    trackNumber = trackNumber ?: 0,
)
