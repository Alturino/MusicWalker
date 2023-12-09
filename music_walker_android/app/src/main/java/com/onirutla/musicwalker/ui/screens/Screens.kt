package com.onirutla.musicwalker.ui.screens

sealed class Screens(val route: String) {
    data object MainScreen : Screens("/")
    data object DetailMusicScreen : Screens("/music")
}
