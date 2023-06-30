package com.example.todoapp

import androidx.room.Query
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM todoitem")
    fun getAll(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todoitem WHERE text = :text")
    fun getByItemText(text: String): Flow<List<TodoItem>>

}