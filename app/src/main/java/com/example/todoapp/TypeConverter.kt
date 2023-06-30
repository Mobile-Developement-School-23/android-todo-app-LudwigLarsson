package com.example.todoapp

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class TypeConverter {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun fromImportance(importance: TodoItem.Importance?): String? {
        return importance?.toString()
    }

    @TypeConverter
    fun toImportance(importance: String?): TodoItem.Importance? {
        return importance?.let { TodoItem.Importance.valueOf(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): String? {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru", "RU"))
        return date?.let { dateFormat.format(it) }
    }

    @TypeConverter
    fun toDate(date: String?): Date? {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru", "RU"))
        return date?.let { dateFormat.parse(it) }
    }
}