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
import kotlinx.coroutines.launch
import java.util.Locale


class MainFragment : Fragment() {
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
                    text = it.text
                )
            view.findNavController().navigate(action)
        })
        recyclerView.adapter = adapter
        lifecycle.coroutineScope.launch {
            viewModel.fullItems().collect() {
                adapter.submitList(it)
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // it will triggered when
                // we submit the written test
                return true
            }
            // this function will triggered when we
            // write even a single char in search view
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
        // %" "% because our custom sql query will require that
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.submitList(it)
            }
        })
    }
}