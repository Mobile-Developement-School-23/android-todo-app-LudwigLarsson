package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.todoapp.DI.ComponentFragments
import com.example.todoapp.DI.DaggerNewTaskComponentFragments
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentNewTaskBinding
import javax.inject.Inject

class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding

    @Inject
    lateinit var newTaskFragmentView: NewTaskFragmentView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_task,
            container,
            false
        )

        val component = ComponentFragments(
            requireActivity(),
            binding.root,
            viewLifecycleOwner
        )

        DaggerNewTaskComponentFragments.builder()
            .componentFragment(component)
            .build()
            .inject(this)

        newTaskFragmentView.init()
        return binding.root
    }

}