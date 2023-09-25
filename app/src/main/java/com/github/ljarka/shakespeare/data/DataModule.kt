package com.github.ljarka.shakespeare.data

import com.github.ljarka.shakespeare.domain.DataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface DataModule {

    @Binds
    fun provideDataSource(localDataSource: LocalDataSource): DataSource
}