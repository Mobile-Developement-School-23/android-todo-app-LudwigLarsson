package com.example.todoapp.repository

import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.retrofit.ResponseEl
import com.example.todoapp.model.ToDoApplication
import com.example.todoapp.model.TodoItem
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit


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
    fun getTodoItems(): Flow<List<TodoItem>>{
        return dao.getAll()}

    companion object {
        private val repository: TodoItemsRepository? = null

        fun getInstance(application: ToDoApplication): TodoItemsRepository =
            repository ?: TodoItemsRepository(application)

    }


    suspend fun insertTodo(item: TodoItem) {
        dao.insertToDo(item)
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
    suspend fun searchDatabase(searchQuery: String): Flow<List<TodoItem>> {
        return dao.searchDatabase(searchQuery)
    }

}