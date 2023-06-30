package com.example.todoapp

import android.app.Application

class ToDoApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}