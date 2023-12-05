package com.onirutla.musicwalker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.onirutla.musicwalker.ui.theme.MusicWalkerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicWalkerApp(modifier = Modifier, navController = rememberNavController())
        }
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
