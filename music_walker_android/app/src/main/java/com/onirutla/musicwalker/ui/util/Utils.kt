package com.onirutla.musicwalker.ui.util

import kotlin.time.Duration.Companion.milliseconds

fun Long.toStringTime(): String = milliseconds.toComponents { minutes, seconds, _ ->
    String.format("%02d:%02d", minutes, seconds)
}
