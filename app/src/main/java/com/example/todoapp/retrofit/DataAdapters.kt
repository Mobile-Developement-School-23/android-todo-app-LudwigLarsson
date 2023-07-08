package com.example.todoapp.retrofit

import com.example.todoapp.model.TodoItem
import com.google.gson.*
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject


class ImportanceAdapter @Inject constructor(): JsonSerializer<TodoItem.Importance>, JsonDeserializer<TodoItem.Importance> {
    override fun serialize(
        src: TodoItem.Importance?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.string ?: throw IllegalStateException())
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TodoItem.Importance {
        val level = json?.asString
        return when (level) {
            "common" -> TodoItem.Importance.COMMON
            "low" -> TodoItem.Importance.LOW
            "high" -> TodoItem.Importance.HIGH
            else -> throw JsonParseException("Wrong level of importance: $level")
        }
    }
}

class DateJsonAdapter @Inject constructor(): JsonSerializer<Date>, JsonDeserializer<Date> {
    override fun serialize(
        src: Date,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(src.time / 1000)
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Date {
        return Date(json.asLong * 1000)
    }
}

class UUIDAdapter @Inject constructor(): JsonSerializer<UUID>, JsonDeserializer<UUID> {
    override fun serialize(
        src: UUID?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): UUID {
        return UUID.fromString(json?.asString)
    }
}