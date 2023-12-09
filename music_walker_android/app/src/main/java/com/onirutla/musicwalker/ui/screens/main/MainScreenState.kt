package com.onirutla.musicwalker.ui.screens.main

import com.onirutla.musicwalker.domain.models.Music

data class MainScreenState(
    val musics: List<Music> = listOf(),
    val selectedMusic: Music? = null,
    val isLoading: Boolean? = null,
)
