package com.example.todoapp.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentNewTaskBinding
import com.example.todoapp.model.ItemViewModel
import com.example.todoapp.model.ItemViewModelFactory
import com.example.todoapp.model.ToDoApplication
import com.example.todoapp.model.TodoItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject


class NewTaskFragmentView @Inject constructor(
    private val activity: Activity,
    private val rootView: View,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: ItemViewModel,
) {

    private var importance: TodoItem.Importance = TodoItem.Importance.LOW
    private var deadline: Date? = null

    val delete: Button = rootView.findViewById(R.id.delete)
    val save: Button = rootView.findViewById(R.id.save)
    val close: ImageView = rootView.findViewById(R.id.close)
    val created = rootView.findViewById<TextView>(R.id.created)
    val switchh = rootView.findViewById<Switch>(R.id.switchh)
    val date = rootView.findViewById<TextView>(R.id.date)

    fun init() {
        created.visibility = View.GONE

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
                rootView.findNavController().popBackStack()
            }
        })
        initDatePicker()
    }

    private fun saveButtonClicked(): Boolean {
        val editText = rootView.findViewById<EditText>(R.id.smthtodo)
        val temp = editText.text.toString()
        if (temp.trim().isEmpty()) {
            editText.error = "Введите текст дела"
            return true
        }

        val currentDate = Date()
        val spinner = rootView.findViewById<Spinner>(R.id.spinner)
        val position = spinner.selectedItemPosition
        when(position) {
            0 -> importance = TodoItem.Importance.COMMON
            1 -> importance = TodoItem.Importance.LOW
            2 -> importance = TodoItem.Importance.HIGH
        }

        val newToDo = TodoItem(
            text = temp,
            importance = importance,
            deadline = if (switchh.isChecked) deadline else null,
            flag = TodoItem.Completed.DISCOMPLETED,
            creationDate = Date(),
            changeDate = currentDate.time / 1000
        )
        viewModel.insertToDo(newToDo)
        rootView.findNavController().popBackStack()
        return true
    }

    private fun initDatePicker() {
        val datePicker = DatePickerDialog(activity)

        datePicker.setOnCancelListener {
            switchh.isChecked = false
        }

        datePicker.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, monthOfYear, dayOfMonth)
            val currDate = cal.time

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru", "RU"))
            val formattedDate = dateFormat.format(currDate)

            deadline = currDate
            date.text = formattedDate
            date.visibility = View.VISIBLE
        }

        switchh.setOnCheckedChangeListener { _, b ->
            if (b) {
                val cal = Calendar.getInstance()
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val month = cal.get(Calendar.MONTH)
                val year = cal.get(Calendar.YEAR)
                datePicker.show()
                datePicker.updateDate(year, month, day)
            } else {
                date.visibility = View.GONE
            }
        }
    }
}