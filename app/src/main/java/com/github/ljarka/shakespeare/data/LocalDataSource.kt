package com.github.ljarka.shakespeare.data

import android.content.Context
import com.github.ljarka.shakespeare.R
import com.github.ljarka.shakespeare.domain.DataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : DataSource {

    override suspend fun getData(): String {
        return context.resources.openRawResource(R.raw.data_source).use {
            it.reader().readText()
        }
    }
}