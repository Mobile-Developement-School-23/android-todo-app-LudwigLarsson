package com.example.todoapp

import java.text.SimpleDateFormat
import java.util.*

data class TodoItem(val id: String, val text: String, val importance: Importance, val deadline: Date? = Date(), val flag: Completed, val creationDate: Date, val changeDate: Date? = Date()) {
    enum class Importance {
        LOW,
        COMMON,
        HIGH,
    } enum class Completed {
            COMPLETED,
            DISCOMPLETED
        }
    }
