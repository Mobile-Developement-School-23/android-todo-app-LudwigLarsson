package com.example.todoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(context: Context?, items: ArrayList<TodoItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater
    private val items: ArrayList<TodoItem>
    private val context: Context?

    init {
        inflater = LayoutInflater.from(context)
        this.items = items
        this.context = context
    }

    private inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView
        val checkBox: CheckBox

        init {
            text = itemView.findViewById<TextView>(R.id.itemText)
            checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = inflater.inflate(R.layout.item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val w: TodoItem = items[position]
        (holder as MyHolder).text.setText(w.text)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}