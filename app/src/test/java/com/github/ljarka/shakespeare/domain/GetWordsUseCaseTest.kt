package com.github.ljarka.shakespeare.domain

import com.github.ljarka.shakespeare.ui.WordItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class GetWordsUseCaseTest {
    private val dataSource = mockk<DataSource>()
    private lateinit var useCase: GetWordsUseCase

    @Before
    fun setUp() {
        useCase = GetWordsUseCase(dataSource)
    }

    @Test
    fun `should extract words with frequency`() = runTest {
        coEvery { dataSource.getData() } returns "hello world"

        val result = useCase()

        assertThat(result)
            .containsExactly(
                WordItem("hello", 1),
                WordItem("world", 1)
            )
    }

    @Test
    fun `should count multiple occurrences of the same word`() = runTest {
        coEvery { dataSource.getData() } returns "hello hello"

        val result = useCase()

        assertThat(result)
            .containsExactly(
                WordItem("hello", 2)
            )
    }

    @Test
    fun `should remove special characters and count words`() = runTest {
        coEvery { dataSource.getData() } returns "hello,!?(); hello"

        val result = useCase()

        assertThat(result)
            .containsExactly(
                WordItem("hello", 2)
            )
    }

    @Test
    fun `should split words at new lines and count`() = runTest {
        coEvery { dataSource.getData() } returns "hello hello\nhello"

        val result = useCase()

        assertThat(result)
            .containsExactly(
                WordItem("hello", 3)
            )
    }

    @Test
    fun `should handle empty input`() = runTest {
        coEvery { dataSource.getData() } returns ""

        val result = useCase()

        assertThat(result).isEmpty()
    }

    @Test
    fun `should ignore special characters only input`() = runTest {
        coEvery { dataSource.getData() } returns "!@#$%^&*()"

        val result = useCase()

        assertThat(result).isEmpty()
    }

    @Test
    fun `should handle mixed case words`() = runTest {
        coEvery { dataSource.getData() } returns "Hello hello HELLO"

        val result = useCase()

        assertThat(result)
            .containsExactly(
                WordItem("hello", 3)
            )
    }

    @Test
    fun `should handle numbers in input`() = runTest {
        coEvery { dataSource.getData() } returns "hello 123 hello"

        val result = useCase()

        assertThat(result)
            .containsExactly(
                WordItem("hello", 2),
                WordItem("123", 1)
            )
    }
}