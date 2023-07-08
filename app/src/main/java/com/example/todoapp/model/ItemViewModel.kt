package com.example.todoapp.model

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.database.ItemDao
import com.example.todoapp.repository.TodoItemsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemViewModel(
    val application: ToDoApplication,
    val repository: TodoItemsRepository,
) : AndroidViewModel(application) {
    private val database by lazy { AppDatabase.getDatabase(application) }

    private val itemDao by lazy { database.itemDao() }
    private val job = Job()
    var savedToDoItem: TodoItem? = null
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("Coroutine exception Handler", "Error: ${throwable.message}")
        }
    private val scope =
        CoroutineScope(Dispatchers.IO + job + coroutineExceptionHandler + CoroutineName("ItemViewModelCustomScope"))

    fun fullItems(): Flow<List<TodoItem>> = itemDao.getAll()
    fun insertToDo(newToDo: TodoItem) {
        scope.launch {
            val networkResult = repository.insertTodo(newToDo)
        }
    }
    fun updateToDo(todoItem: TodoItem) {
        scope.launch {
            val networkResult = repository.updateToDo(todoItem)
        }
    }

    fun deleteToDo(todoItem: TodoItem) {
        scope.launch {
            val networkResult = repository.deleteToDo(todoItem)
        }
    }

    suspend fun searchDatabase(searchQuery: String): LiveData<List<TodoItem>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}


class ItemViewModelFactory@Inject constructor(
    val application: ToDoApplication,
    val repository: TodoItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemViewModel(application, repository) as T
    }
}