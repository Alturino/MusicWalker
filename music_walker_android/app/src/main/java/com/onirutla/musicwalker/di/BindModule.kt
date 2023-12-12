package com.onirutla.musicwalker.di

import com.onirutla.musicwalker.core.media.playback_controller.MusicWalkerPlaybackController
import com.onirutla.musicwalker.core.media.playback_controller.PlaybackController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    @Singleton
    abstract fun bindPlaybackController(musicWalkerPlaybackController: MusicWalkerPlaybackController): PlaybackController

}
