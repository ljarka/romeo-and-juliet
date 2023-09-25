package com.github.ljarka.shakespeare

import com.github.ljarka.shakespeare.utils.DefaultDispatcherProvider
import com.github.ljarka.shakespeare.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDispatcherProvider(
        defaultDispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider = defaultDispatcherProvider
}