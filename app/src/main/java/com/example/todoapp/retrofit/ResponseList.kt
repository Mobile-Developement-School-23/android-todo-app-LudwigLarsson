package com.example.todoapp.retrofit

import com.example.todoapp.model.TodoItem

data class ResponseList(
    val revision: Int,
    val list: List<TodoItem>
)