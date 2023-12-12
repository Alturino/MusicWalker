package com.onirutla.musicwalker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @DispatchersIO
    fun providesDispatchersIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DispatchersDefault
    fun providesDispatchersDefault(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @DispatchersMain
    fun providesDispatchersMain(): CoroutineDispatcher = Dispatchers.Main

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatchersIO

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatchersDefault

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatchersMain
