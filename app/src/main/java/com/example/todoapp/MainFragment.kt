package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import androidx.navigation.findNavController
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    private val viewModel: ItemViewModel by activityViewModels {
        ItemViewModelFactory(
            (activity?.application as ToDoApplication).database.itemDao()
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
        val adapter = Adapter({
            val action = MainFragmentDirections
                .actionMainFragmentToNewTaskFragment(
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
    }
}