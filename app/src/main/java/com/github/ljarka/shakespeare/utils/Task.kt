package com.github.ljarka.shakespeare.utils

internal class Task private constructor(
    val state: State,
    val error: Throwable? = null
) {

    enum class State {
        SUCCESS, FAILURE, IDLE, RUNNING
    }

    fun isSuccess() = state == State.SUCCESS

    fun isError() = state == State.FAILURE

    fun isRunning() = state == State.RUNNING

    companion object {
        fun idle() = Task(state = State.IDLE)
        fun running() = Task(state = State.RUNNING)
        fun failure(throwable: Throwable? = null) = Task(state = State.FAILURE, error = throwable)
        fun success() = Task(state = State.SUCCESS)
    }
}