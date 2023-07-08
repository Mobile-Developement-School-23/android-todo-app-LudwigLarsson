package com.example.todoapp.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

open class EditFragment : Fragment() {

    private lateinit var _binding: FragmentNewTaskBinding
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var argumentsFromMain: EditFragmentArgs

    private lateinit var item: TodoItem

    private var deadline: Date? = null

    private var importance: TodoItem.Importance = TodoItem.Importance.LOW

    private val viewModel: ItemViewModel by activityViewModels {
        ItemViewModelFactory(
            (activity?.application as ToDoApplication)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (savedInstanceState != null) {
            item = viewModel.savedToDoItem ?: throw IllegalStateException("my error")
        } else {
            argumentsFromMain = EditFragmentArgs.fromBundle(requireArguments())
            item = argumentsFromMain.todoitem
        }
        _binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        initDatePicker()
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = binding.root.findNavController()

        initChangeLogic()

        initDatePicker()

        binding.close.setOnClickListener {
            navController.popBackStack()
        }

        binding.delete.setOnClickListener {
            deleteToDo()
        }

        binding.save.setOnClickListener { saveButtonOnClick() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(FLAG, 1)

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

    private fun initChangeLogic() {
        setData()
        taskCompleted()
    }

    private fun setData() {
        importance = item.importance
        val editableTitle: Editable? =
            item.text.let { Editable.Factory.getInstance().newEditable(it) }
        binding.smthtodo.text = editableTitle

            // Важность дела

        val converter = SimpleDateFormat("dd.MM.yy", Locale("ru", "RU"))
        val convertedDeadline = item.deadline?.let { converter.format(it) }
        val convertedCreationDate = converter.format(item.creationDate)

            // Крайний срок выполнения дела
        item.deadline?.let { itemDeadline ->
            deadline = itemDeadline
            binding.deadline.visibility = View.VISIBLE
            binding.date.text = convertedDeadline
            binding.switchh.isChecked = true
        }

            // Дата создания - видима
        binding.creationDate.text = convertedCreationDate
        binding.created.visibility = View.VISIBLE

            // Кнопка удаления - работает
        binding.delete.isClickable = true
        binding.del.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
        binding.delete.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
    }

    private fun taskCompleted() {
        if (item.flag == TodoItem.Completed.DISCOMPLETED) return
        with(binding) {
            binding.save.isClickable = false
            binding.save.text = "Выполнено"
            binding.save.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.deadline.visibility = if (deadline != null) View.VISIBLE else View.GONE
            binding.smthtodo.isEnabled = false
            binding.switchh.visibility = View.GONE
        }
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

            deadline = date
            deadlineText.text = deadline.toString()
            deadlineText.visibility = View.VISIBLE
        }

        switch.setOnClickListener {
            if (switch.isChecked) {
                val cal = Calendar.getInstance()
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val month = cal.get(Calendar.MONTH)
                val year = cal.get(Calendar.YEAR)
                datePicker.show()
                datePicker.updateDate(year, month, day)
            } else {
                deadlineText.visibility = View.GONE
                deadline = null
            }
        }
    }

    private fun deleteToDo() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Вы уверены?")
        builder.setPositiveButton("Да") { _, _ ->
            viewModel.deleteToDo(item)
            navController.popBackStack()
        }
        builder.setNegativeButton("Нет") { dialog, _ ->
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun saveButtonOnClick(): Boolean {
        if (item.flag == TodoItem.Completed.COMPLETED) return true

        val editText = binding.smthtodo
        val temp = editText.text.toString()

        if (temp.trim().isEmpty()) {
            editText.error = "Введите текст дела"
            return true
        }

        val currentDate = Date()

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
        navController.popBackStack()
        return true
    }

    companion object {
        private const val FLAG = "82031Y13971317-1319-38PCN8912-19722"
    }
}