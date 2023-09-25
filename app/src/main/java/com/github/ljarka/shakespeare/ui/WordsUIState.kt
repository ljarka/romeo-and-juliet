package com.github.ljarka.shakespeare.ui

import com.github.ljarka.shakespeare.utils.Task

internal data class WordsUIState(
    val words: List<WordItem> = emptyList(),
    val task: Task = Task.idle()
)