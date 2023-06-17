package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository: TodoItemsRepository = TodoItemsRepository()
        val recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter(context, repository.getToDoList())
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerViewAdapter
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.mainFragment, NewTaskFragment())
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
        })
    }
}