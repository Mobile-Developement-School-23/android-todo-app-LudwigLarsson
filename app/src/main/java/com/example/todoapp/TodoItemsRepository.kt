package com.example.todoapp

import java.util.*
import kotlin.collections.ArrayList

class TodoItemsRepository {
    var todoList: ArrayList<TodoItem>
    init {
        todoList = arrayListOf(TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()), TodoItem("1", "Домашка по View", TodoItem.Importance.LOW, Date(), TodoItem.Completed.DISCOMPLETED, Date(), Date()))
    }
    fun addItem(newItem: TodoItem) {
        todoList.add(newItem)
    }
    fun getToDoList() : ArrayList<TodoItem> {
        return todoList
    }
}