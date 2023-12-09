package com.onirutla.musicwalker

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MusicWalkerApp : Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.d("Planting Timber DebugTree")
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber DebugTree is Planted")
        }
    }

    override fun newImageLoader(): ImageLoader = imageLoader
}
