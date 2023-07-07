package com.example.todoapp.model

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemBinding
import java.text.SimpleDateFormat
import java.util.*

class Adapter(
    private val onItemClicked: (TodoItem) -> Unit
) : ListAdapter<TodoItem, Adapter.ItemViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TodoItem>() {
            override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewHolder = ItemViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(
        private var binding: ItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(todoItem: TodoItem) {
            binding.itemText.text = todoItem.text
            /*binding.checkBox.text = SimpleDateFormat(
                "h:mm a").format(
                Date(todoItem.arrivalTime.toLong() * 1000)
            )*/
            if (todoItem.flag == TodoItem.Completed.COMPLETED) {
                binding.checkBox.isChecked = true
                binding.importance.visibility = View.GONE
                binding.deadline.visibility = View.GONE
                binding.tv.visibility = View.GONE
            } else {
                binding.checkBox.isChecked = false
                if (todoItem.deadline != null) {

                    val converter = SimpleDateFormat("dd.MM.yyyy", Locale("ru", "RU"))
                    val convertedDeadline = todoItem.deadline?.let { converter.format(it) }

                    binding.deadline.text = convertedDeadline
                    binding.deadline.visibility = View.VISIBLE
                    binding.tv.visibility = View.VISIBLE

                } else {
                    binding.tv.visibility = View.GONE
                    binding.deadline.visibility = View.GONE
                }
                when (todoItem.importance) {
                    TodoItem.Importance.HIGH -> {
                        with(binding.importance) {
                            visibility = android.view.View.VISIBLE
                            setColorFilter(
                                androidx.core.content.ContextCompat.getColor(
                                    context,
                                    com.example.todoapp.R.color.red
                                ),
                                android.graphics.PorterDuff.Mode.SRC_IN
                            )
                            setImageResource(com.example.todoapp.R.drawable.importance)
                        }
                    }

                    TodoItem.Importance.LOW -> {
                        with(binding.importance) {
                            visibility = android.view.View.VISIBLE
                            setColorFilter(
                                androidx.core.content.ContextCompat.getColor(
                                    context,
                                    com.example.todoapp.R.color.gray
                                ),
                                android.graphics.PorterDuff.Mode.SRC_IN
                            )
                            setImageResource(com.example.todoapp.R.drawable.low_importance)
                        }
                    }

                    else -> {
                        binding.importance.visibility = View.GONE
                    }
                }
            }
        }
    }
}