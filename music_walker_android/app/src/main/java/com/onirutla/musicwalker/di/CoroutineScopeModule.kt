package com.onirutla.musicwalker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @Provides
    @Singleton
    @ApplicationMainCoroutineScope
    fun provideApplicationMainCoroutineScope(@DispatchersMain dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(SupervisorJob() + dispatcher)

    @Provides
    @Singleton
    @ApplicationIOCoroutineScope
    fun provideApplicationIOCoroutineScope(@DispatchersIO dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(SupervisorJob() + dispatcher)
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApplicationMainCoroutineScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApplicationIOCoroutineScope
