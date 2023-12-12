package com.onirutla.musicwalker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.common.util.concurrent.ListenableFuture
import com.onirutla.musicwalker.core.media.music_service.MusicWalkerMediaSessionService
import com.onirutla.musicwalker.ui.theme.MusicWalkerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mediaControllerFuture: ListenableFuture<MediaController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicWalkerApp(modifier = Modifier, navController = rememberNavController())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MediaController.releaseFuture(mediaControllerFuture)
        stopService(Intent(this, MusicWalkerMediaSessionService::class.java))
    }
}

@Composable
fun MusicWalkerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    MusicWalkerTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.surface
        ) {
            MusicWalkerNavigation(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }
    }
}
