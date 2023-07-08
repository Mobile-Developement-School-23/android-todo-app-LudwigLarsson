package com.example.todoapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.database.ItemDao
import com.example.todoapp.retrofit.ResponseEl
import com.example.todoapp.model.ToDoApplication
import com.example.todoapp.model.TodoItem
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class TodoItemsRepository @Inject constructor(
    val application: ToDoApplication,
    val dao: ItemDao,
    val poster: MainService
){

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

    suspend fun insertTodo(item: TodoItem) {
        dao.insertToDo(item)
    }

    suspend fun updateToDo(item: TodoItem) {
        dao.updateToDoItem(item)
    }

    suspend fun deleteToDo(item: TodoItem) {
        dao.deleteTodo(item.id)
        Log.d("delete from repository", item.id.toString())
    }

    /*fun updateData() {
        poster.updateData()
    }*/
    suspend fun searchDatabase(searchQuery: String): Flow<List<TodoItem>> {
        return dao.searchDatabase(searchQuery)
    }

}