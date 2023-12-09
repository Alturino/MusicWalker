package com.onirutla.musicwalker.domain.repository

import com.onirutla.musicwalker.core.data.remote.api_services.MusicApiServices
import com.onirutla.musicwalker.core.data.remote.models.toMusic
import com.onirutla.musicwalker.domain.models.Music
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val apiServices: MusicApiServices,
) {
    suspend fun getMusics(): List<Music> = apiServices.getMusic()
        .musicResponses
        ?.mapNotNull { it?.toMusic() ?: Music() }
        ?: listOf()
}
