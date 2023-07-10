package com.example.todoapp.fragments

import android.app.Activity
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentMainBinding
import com.example.todoapp.model.Adapter
import com.example.todoapp.model.ItemViewModel
import com.example.todoapp.model.TodoItem
import com.example.todoapp.model.onClickCallbacks
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentView @Inject constructor(
    private val activity: Activity,
    private val rootView: View,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: ItemViewModel,
) : onClickCallbacks {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter


    suspend fun init() {
        val fab = rootView.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                rootView.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToNewTaskFragment())
            }
        })

        recyclerView = rootView.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = Adapter({
            val action = MainFragmentDirections.actionMainFragmentToNewTaskFragment(
            )
            rootView.findNavController().navigate(action)
        }, this, viewModel)
        recyclerView.adapter = adapter
        viewModel.fullItems().collect() {
            adapter.submitList(it)
        }
        val search = rootView.findViewById<SearchView>(R.id.search)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                lifecycleOwner.lifecycle.coroutineScope.launch {
                    if (query != null) {
                        searchDatabase(query)
                    }
                }
                return true
            }
        })

    }

    private suspend fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(lifecycleOwner, { list ->
            list.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onItemClick(item: TodoItem) {
        rootView.findNavController()
            .navigate(MainFragmentDirections.actionMainFragmentToEditFragment(item))
    }

}
