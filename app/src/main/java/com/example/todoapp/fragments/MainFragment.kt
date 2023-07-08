package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentMainBinding
import com.example.todoapp.model.Adapter
import com.example.todoapp.model.ItemViewModel
import com.example.todoapp.model.ItemViewModelFactory
import com.example.todoapp.model.ToDoApplication
import com.example.todoapp.model.TodoItem
import com.example.todoapp.model.onClickCallbacks
import kotlinx.coroutines.launch
import java.util.Locale


class MainFragment : Fragment(), onClickCallbacks {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var adapter: Adapter

    private val viewModel: ItemViewModel by activityViewModels {
        ItemViewModelFactory(
            (activity?.application as ToDoApplication)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val navHostFragment = activity?.supportFragmentManager
                    ?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navController = navHostFragment.navController
                navController.navigate(R.id.newTaskFragment)
            }
        })

        recyclerView = binding.recycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = Adapter({
            val action = MainFragmentDirections.actionMainFragmentToNewTaskFragment(
                )
            view.findNavController().navigate(action)
        }, this)
        recyclerView.adapter = adapter
        lifecycle.coroutineScope.launch {
            viewModel.fullItems().collect() {
                adapter.submitList(it)
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                lifecycle.coroutineScope.launch {
                    if(query != null){
                        searchDatabase(query)
                    }
                }
                return true
            }
        })

    }
    private suspend fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.submitList(it)
            }
        })
    }
    override fun onItemClick(item: TodoItem) {
        binding.root.findNavController()
            .navigate(MainFragmentDirections.actionMainFragmentToEditFragment(item))
    }
}