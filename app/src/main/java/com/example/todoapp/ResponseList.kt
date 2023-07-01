package com.example.todoapp

data class ResponseList(
    val revision: Int,
    val list: List<TodoItem>
)