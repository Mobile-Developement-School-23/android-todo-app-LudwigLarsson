package com.example.todoapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switch: Switch = view.findViewById(R.id.switchh)
        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        val delete: Button = view.findViewById<Button>(R.id.delete)
        val save: Button = view.findViewById<Button>(R.id.save)
        val cancel: Button = view.findViewById<Button>(R.id.cancel)
        val ready: Button = view.findViewById<Button>(R.id.ready)
        val close: ImageView = view.findViewById<ImageView>(R.id.close)
        val date: TextView = view.findViewById((R.id.date))

        save.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                fragmentManager?.popBackStack()
            }
        })
        delete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                fragmentManager?.popBackStack()
            }
        })
        close.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                fragmentManager?.popBackStack()
            }
        })

        calendarView.visibility = View.INVISIBLE
        cancel.visibility = View.INVISIBLE
        ready.visibility = View.INVISIBLE
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val calendarView: CalendarView = view.findViewById(R.id.calendarView)
                calendarView.visibility = View.VISIBLE
                cancel.visibility = View.VISIBLE
                ready.visibility = View.VISIBLE
                delete.visibility = View.INVISIBLE
                save.visibility = View.INVISIBLE
            }else{
                date.text = ""
            }
        }
        cancel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                calendarView.visibility = View.INVISIBLE
                cancel.visibility = View.INVISIBLE
                ready.visibility = View.INVISIBLE
                delete.visibility = View.VISIBLE
                save.visibility = View.VISIBLE
            }
        })
        calendarView.setOnDateChangeListener { view, year, month, day ->
            date.text = "$day/$month/$year"
            val c = Calendar.getInstance()
            c.set(year, month, day)
        }

        ready.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                /*val selectedDate = calendarView.date
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selectedDate
                val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
                date.text = "${dateFormatter.format(calendar.time)}"*/
                calendarView.visibility = View.INVISIBLE
                cancel.visibility = View.INVISIBLE
                ready.visibility = View.INVISIBLE
                delete.visibility = View.VISIBLE
                save.visibility = View.VISIBLE
            }
        })
        /*calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date.text = "$dayOfMonth.${month + 1}.$year"
        }*/
    }

}