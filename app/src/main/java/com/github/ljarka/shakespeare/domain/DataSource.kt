package com.github.ljarka.shakespeare.domain

internal interface DataSource {

    suspend fun getData(): String
}