package com.github.ljarka.shakespeare.domain

import com.github.ljarka.shakespeare.ui.WordItem
import javax.inject.Inject

internal class GetWordsUseCase @Inject constructor(
    private val dataSource: DataSource
) {

    suspend operator fun invoke(): List<WordItem> {
        return dataSource.getData()
            .cleanUp()
            .extractWords()
            .countWords()
            .map { WordItem(it.key, it.value) }
            .sortedByDescending { it.frequency }
    }

    private fun String.cleanUp(): String {
        return replace("[.();?,!@#\$%^&*]".toRegex(), "")
    }

    private fun String.extractWords(): List<String> = split("\\s+".toRegex())
        .map { it.trim().lowercase() }

    private fun List<String>.countWords(): Map<String, Int> {
        val words = mutableMapOf<String, Int>()
        filter { it.isNotBlank() }
            .forEach { word ->
                val counter = words.getOrPut(word) { 0 }
                words[word] = counter + 1
            }
        return words
    }
}