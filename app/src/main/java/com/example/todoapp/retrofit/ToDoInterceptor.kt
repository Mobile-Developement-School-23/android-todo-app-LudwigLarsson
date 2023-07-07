package com.example.todoapp.retrofit

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ToDoInterceptor : Interceptor {
    companion object {
        private const val TOKEN = "trilobated1"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "Bearer $TOKEN")
            .build()
        return chain.proceed(request)
    }
}