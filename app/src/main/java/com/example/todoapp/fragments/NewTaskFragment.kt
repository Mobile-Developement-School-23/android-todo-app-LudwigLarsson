package com.example.todoapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentMainBinding
import com.example.todoapp.databinding.FragmentNewTaskBinding
import com.example.todoapp.model.ItemViewModel
import com.example.todoapp.model.ItemViewModelFactory
import com.example.todoapp.model.ToDoApplication
import com.example.todoapp.model.TodoItem
import java.text.SimpleDateFormat
import java.util.*

class NewTaskFragment : Fragment() {
    private var _binding: FragmentNewTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private var importance: TodoItem.Importance = TodoItem.Importance.LOW
    private var deadline: Date? = null
    private val viewModel: ItemViewModel by activityViewModels {
        ItemViewModelFactory(
            (activity?.application as ToDoApplication)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        initDatePicker()
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = binding.root.findNavController()
        val delete: Button = binding.delete
        val save: Button = binding.save
        val close: ImageView = binding.close
        binding.created.visibility = View.GONE

        save.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                saveButtonClicked()
            }
        })
        delete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
            }
        })
        close.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                navController.popBackStack()
            }
        })
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("flag", 1)

        val editText = binding.smthtodo
        val temp = editText.text.toString()
        val currentDate = Date()

        val itemForSave = TodoItem(
            id = UUID.randomUUID(),
            text = temp,
            importance = importance,
            deadline = deadline,
            flag = TodoItem.Completed.DISCOMPLETED,
            creationDate = Date(),
            changeDate = currentDate.time / 1000
        )
        viewModel.savedToDoItem = itemForSave
    }
    private fun saveButtonClicked(): Boolean {
        val editText = binding.smthtodo
        val temp = editText.text.toString()
        if (temp.trim().isEmpty()) {
            editText.error = "Введите текст дела"
            return true
        }

        val currentDate = Date()
        val position = binding.spinner.selectedItemPosition
        when(position) {
            0 -> importance = TodoItem.Importance.COMMON
            1 -> importance = TodoItem.Importance.LOW
            2 -> importance = TodoItem.Importance.HIGH
        }

        val newToDo = TodoItem(
            text = temp,
            importance = importance,
            deadline = if (binding.switchh.isChecked) deadline else null,
            flag = TodoItem.Completed.DISCOMPLETED,
            creationDate = Date(),
            changeDate = currentDate.time / 1000
        )
        viewModel.insertToDo(newToDo)
        navController.popBackStack()
        return true
    }

    private fun initDatePicker() {
        val datePicker = DatePickerDialog(requireContext())
        val switch = binding.switchh
        val deadlineText = binding.date

        datePicker.setOnCancelListener {
            switch.isChecked = false
        }

        datePicker.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, monthOfYear, dayOfMonth)
            val date = cal.time

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru", "RU"))
            val formattedDate = dateFormat.format(date)

            deadline = date
            deadlineText.text = formattedDate
            deadlineText.visibility = View.VISIBLE
        }

        switch.setOnCheckedChangeListener { _, b ->
            if (b) {
                val cal = Calendar.getInstance()
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val month = cal.get(Calendar.MONTH)
                val year = cal.get(Calendar.YEAR)
                datePicker.show()
                datePicker.updateDate(year, month, day)
            } else {
                deadlineText.visibility = View.GONE
            }
        }
    }

}