package com.example.todoapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class Regular(val context: Context, parameters: WorkerParameters): Worker(context, parameters) {
    override fun doWork(): Result {
        return try {
            val repo = TodoItemsRepository.getInstance(context.applicationContext as ToDoApplication)
            repo.updateData()
            Result.success()
        } catch (e : Exception) {
            Result.failure() }
    }
}