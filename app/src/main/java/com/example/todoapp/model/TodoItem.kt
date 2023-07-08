package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class TodoItem(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "importance") val importance: Importance,
    @ColumnInfo(name = "deadline") val deadline: Date? = Date(),
    @ColumnInfo(name = "flag") val flag: Completed,
    @ColumnInfo(name = "creation_date") val creationDate: Date,
    @ColumnInfo(name = "change_date") val changeDate: Long = 1
) : Serializable {
    enum class Importance(val string: String) {
        LOW("low"),
        COMMON("common"),
        HIGH("high"),
    }

    enum class Completed {
        COMPLETED,
        DISCOMPLETED
    }
}
