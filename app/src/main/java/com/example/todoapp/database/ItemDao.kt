package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.example.todoapp.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM todoitem")
    fun getAll(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todoitem WHERE text = :text")
    fun getByItemText(text: String): Flow<List<TodoItem>>

    @Insert
    suspend fun insertToDo(newToDo: TodoItem)

    @Query("SELECT * FROM todoitem")
    fun getToDoItems(): LiveData<List<TodoItem>?>

    @Query("DELETE FROM todoitem WHERE id =(:ToDoItemId)")
    fun deleteTodo(ToDoItemId: String)
    @Update
    fun updateToDoItem(itemToUpdate: TodoItem)
    @Query("SELECT * FROM todoitem")
    suspend fun joinToDoItems(): List<TodoItem>?

    @Query("SELECT * FROM todoitem WHERE text LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<TodoItem>>

}