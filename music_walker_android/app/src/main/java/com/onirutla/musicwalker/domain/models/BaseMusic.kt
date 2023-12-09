package com.onirutla.musicwalker.domain.models

import com.onirutla.musicwalker.core.data.remote.models.MusicResponse

data class BaseMusic(
    val music: List<MusicResponse> = listOf(),
)
