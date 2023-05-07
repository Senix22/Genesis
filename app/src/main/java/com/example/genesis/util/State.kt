package com.example.genesis.util

sealed class State<out T, out E> {
    data class Content<T>(val data: T) : State<T, Nothing>()
    data class Error<E>(val error: E) : State<Nothing, E>()

    object Loading : State<Nothing, Nothing>()
    object Empty : State<Nothing, Nothing>()
}