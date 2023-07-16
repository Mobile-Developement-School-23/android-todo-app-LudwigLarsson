package com.example.todoapp.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.example.todoapp.R
import com.example.todoapp.model.ItemViewModel
import com.example.todoapp.model.TodoItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class EditFragmentView @Inject constructor(
    private val activity: Activity,
    private val rootView: View,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: ItemViewModel,
) {

    private lateinit var item: TodoItem

    private var deadline: Date? = null

    private var importance: TodoItem.Importance = TodoItem.Importance.LOW
    private val supportFragmentManager by lazy { activity.fragmentManager }

    val close = rootView.findViewById<ImageView>(R.id.close)
    val delete = rootView.findViewById<Button>(R.id.delete)
    val save = rootView.findViewById<Button>(R.id.save)
    val deadlinetv = rootView.findViewById<TextView>(R.id.deadline)
    val date = rootView.findViewById<TextView>(R.id.date)
    val switchh = rootView.findViewById<Switch>(R.id.switchh)
    val creationDate = rootView.findViewById<TextView>(R.id.creationDate)
    val created = rootView.findViewById<TextView>(R.id.created)
    val smthtodo = rootView.findViewById<EditText>(R.id.smthtodo)

    //private lateinit var argumentsFromMain: EditFragmentArgs
    fun configurationChange() {
        item = viewModel.savedToDoItem ?: throw IllegalStateException("my error")
    }

    fun setArgumentsFromMain(todo: TodoItem) {
        item = todo
    }

    fun init() {
        initChangeLogic()

        initDatePicker()

        close.setOnClickListener {
            rootView.findNavController().popBackStack()
        }

        delete.setOnClickListener {
            deleteToDo()
        }

        save.setOnClickListener { saveButtonOnClick() }
    }


    private fun initChangeLogic() {
        setData()
        taskCompleted()
    }

    private fun setData() {
        val editableTitle: Editable? =
            item.text.let { Editable.Factory.getInstance().newEditable(it) }
        smthtodo.text = editableTitle

        delete.setTextColor(Color.parseColor("#FE0000"))

        val converter = SimpleDateFormat("dd.MM.yy", Locale("ru", "RU"))
        val convertedDeadline = item.deadline?.let { converter.format(it) }
        val convertedCreationDate = converter.format(item.creationDate)


        item.deadline?.let { itemDeadline ->
            deadline = itemDeadline
            deadlinetv.visibility = View.VISIBLE
            date.text = convertedDeadline
            switchh.isChecked = true
        }

        creationDate.text = convertedCreationDate
        created.visibility = View.VISIBLE

        delete.isClickable = true
    }


    private fun taskCompleted() {
        if (item.flag == TodoItem.Completed.DISCOMPLETED) return
        save.isClickable = false
        save.text = "Выполнено"
        save.setTextColor(
            ContextCompat.getColor(
                activity,
                R.color.green
            )
        )
        deadlinetv.visibility = if (deadline != null) View.VISIBLE else View.GONE
        smthtodo.isEnabled = false
        switchh.visibility = View.GONE
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

            deadline = currDate
            date.text = deadline.toString()
            date.visibility = View.VISIBLE
        }

        switchh.setOnClickListener {
            if (switchh.isChecked) {
                val cal = Calendar.getInstance()
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val month = cal.get(Calendar.MONTH)
                val year = cal.get(Calendar.YEAR)
                datePicker.show()
                datePicker.updateDate(year, month, day)
            } else {
                date.visibility = View.GONE
                deadline = null
            }
        }
    }

    private fun deleteToDo() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Вы уверены?")
        builder.setPositiveButton("Да") { _, _ ->
            viewModel.deleteToDo(item)
            delete.setTextColor(Color.parseColor("#FF6767"))
            rootView.findNavController().popBackStack()
        }
        builder.setNegativeButton("Нет") { dialog, _ ->
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun saveButtonOnClick(): Boolean {
        if (item.flag == TodoItem.Completed.COMPLETED) return true

        val temp = smthtodo.text.toString()

        if (temp.trim().isEmpty()) {
            smthtodo.error = "Введите текст дела"
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
            id = item.id,
            text = temp,
            importance = importance,
            deadline = deadline,
            flag = TodoItem.Completed.DISCOMPLETED,
            creationDate = item.creationDate,
            changeDate = currentDate.time / 1000
        )
        viewModel.updateToDo(newToDo)
        rootView.findNavController().popBackStack()
        return true
    }
    interface BottomSheetInterface {
        fun selectImportance()
    }
}