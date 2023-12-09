package com.onirutla.musicwalker.core.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse(
    @SerialName("music")
    val musicResponses: List<MusicResponse?>? = listOf(),
)
