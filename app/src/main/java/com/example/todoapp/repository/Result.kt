package com.example.todoapp.repository

sealed class Result<T : Any> {
    data class Exception<T: Any>(val e: Throwable): Result<T>()
    data class Success<T : Any>(val data: T) : Result<T>()
    data class Error<T: Any>(val code: Int, val message: String?): Result<T>()
}