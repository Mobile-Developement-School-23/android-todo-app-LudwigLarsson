package com.example.todoapp

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class TodoItemsRepository private constructor(val application: ToDoApplication){
    private val database by lazy { AppDatabase.getDatabase(application) }

    private val dao by lazy { database.itemDao() }

    private val poster by lazy { MainService(application) }

    init {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workPeriodicRequest = PeriodicWorkRequestBuilder<Regular>(8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(application).enqueue(workPeriodicRequest)
    }
    fun getToDoItems(): LiveData<List<TodoItem>?> = dao.getToDoItems()

    companion object {
        private val repository: TodoItemsRepository? = null

        fun getInstance(application: ToDoApplication): TodoItemsRepository =
            repository ?: TodoItemsRepository(application)

    }


    suspend fun insertTodo(item: TodoItem): Result<ResponseEl>? {
        dao.insertToDo(item).also {
            if (application.isInternet()) return poster.insertToDo(item)
        }
        return null
    }

    suspend fun updateToDo(item: TodoItem): Result<ResponseEl>? {
        dao.updateToDoItem(item).also {
            if (application.isInternet()) return poster.updateToDo(item)
        }
        return null
    }

    suspend fun deleteToDo(item: TodoItem): Result<ResponseEl>? {
        dao.updateToDoItem(item.apply { }).also {
            if (application.isInternet()) return poster.updateToDo(item)
        }
        return null
    }

    fun updateData() {
        poster.updateData()
    }

}