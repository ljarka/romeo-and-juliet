package com.github.ljarka.shakespeare.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ljarka.shakespeare.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsActivity : ComponentActivity() {

    private val wordsViewModel by viewModels<WordsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(wordsViewModel) {
                        wordsViewModel.fetchWords()
                    }

                    val state = wordsViewModel.wordsState.collectAsState().value

                    if (state.task.isSuccess()) {
                        LazyColumn {
                            items(state.words) {
                                Item(name = it.word, frequency = it.frequency)
                            }
                        }
                    } else if (state.task.isRunning()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Item(name: String, frequency: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(16.dp)) {
        Text(text = name)
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = frequency.toString())
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    MyApplicationTheme {
        Item(name = "Word", frequency = 12)
    }
}