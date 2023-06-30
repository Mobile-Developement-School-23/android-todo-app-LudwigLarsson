package com.example.todoapp

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class TodoItem(@PrimaryKey val id: String, @ColumnInfo(name = "text") val text: String, @ColumnInfo(name = "importance") val importance: Importance, @ColumnInfo(name = "deadline") val deadline: Date? = Date(), @ColumnInfo(name = "flag") val flag: Completed, @ColumnInfo(name = "creation_date") val creationDate: Date, @ColumnInfo(name = "change_date") val changeDate: Date? = Date()) {
    enum class Importance {
        LOW,
        COMMON,
        HIGH,
    } enum class Completed {
            COMPLETED,
            DISCOMPLETED
        }
    }
