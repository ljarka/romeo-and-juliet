package com.github.ljarka.shakespeare.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ljarka.shakespeare.domain.GetWordsUseCase
import com.github.ljarka.shakespeare.utils.DispatcherProvider
import com.github.ljarka.shakespeare.utils.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WordsViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val mutableWordsState = MutableStateFlow(WordsUIState())
    val wordsState: StateFlow<WordsUIState> = mutableWordsState

    fun fetchWords() {
        viewModelScope.launch(dispatcherProvider.io()) {
            mutableWordsState.update { it.copy(task = Task.running()) }
            val words = getWordsUseCase()
            mutableWordsState.update { it.copy(words = words, task = Task.success()) }
        }
    }
}