package com.example.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow

class ItemViewModel(private val itemDao: ItemDao): ViewModel() {

    fun fullItems(): Flow<List<TodoItem>> = itemDao.getAll()

    fun scheduleForStopName(text: String): Flow<List<TodoItem>> = itemDao.getByItemText(text)
}

class ItemViewModelFactory(
    private val itemDao: ItemDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}