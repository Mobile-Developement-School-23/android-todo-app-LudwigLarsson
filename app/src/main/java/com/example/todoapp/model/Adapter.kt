package com.example.todoapp.model

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemBinding
import java.text.SimpleDateFormat
import java.util.*

class Adapter(
    private val onItemClicked: (TodoItem) -> Unit, val callbacks: onClickCallbacks, val viewModel: ItemViewModel
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
        holder.itemView.setOnClickListener {
            callbacks.onItemClick(getItem(position))
        }
        holder.bind(getItem(position), viewModel)
    }

    class ItemViewHolder(
        private var binding: ItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(todoItem: TodoItem, viewModel: ItemViewModel) {
            binding.itemText.text = todoItem.text
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
                            visibility = View.VISIBLE
                            setColorFilter(
                                ContextCompat.getColor(
                                    context,
                                    R.color.red
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                            setImageResource(R.drawable.importance)
                        }
                    }

                    TodoItem.Importance.LOW -> {
                        with(binding.importance) {
                            visibility = View.VISIBLE
                            setColorFilter(
                                ContextCompat.getColor(
                                    context,
                                    R.color.gray
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                            setImageResource(R.drawable.low_importance)
                        }
                    }

                    else -> {
                        binding.importance.visibility = View.GONE
                    }
                }
            }
            val checkBox: CheckBox = binding.checkBox
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                val newToDo = TodoItem(
                    id = todoItem.id,
                    text = todoItem.text,
                    importance = todoItem.importance,
                    deadline = todoItem.deadline,
                    flag = TodoItem.Completed.COMPLETED,
                    creationDate = todoItem.creationDate,
                    changeDate = todoItem.changeDate
                )
                viewModel.updateToDo(newToDo)
            }
        }
    }
}

interface onClickCallbacks {
    fun onItemClick(item: TodoItem)
}