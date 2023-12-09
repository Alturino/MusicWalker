package com.onirutla.musicwalker.core.data.remote.api_services

import com.onirutla.musicwalker.core.data.remote.models.BaseResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import timber.log.Timber
import javax.inject.Inject

class MusicApiServices @Inject constructor(
    private val httpClient: HttpClient,
) {
    suspend fun getMusic(): BaseResponse = try {
        httpClient.get("https://storage.googleapis.com/uamp/catalog.json").body<BaseResponse>()
    } catch (e: Exception) {
        Timber.e(e)
        BaseResponse()
    }
}
