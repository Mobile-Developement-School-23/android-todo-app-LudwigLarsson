package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.todoapp.DI.ComponentFragments
import com.example.todoapp.DI.DaggerEditComponentFragments
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentNewTaskBinding
import javax.inject.Inject

open class EditFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding

    @Inject
    lateinit var editFragmentView: EditFragmentView

    private lateinit var argumentsFromMain: EditFragmentArgs

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

        val component = ComponentFragments (
            requireActivity(),
            binding.root,
            viewLifecycleOwner
        )

        DaggerEditComponentFragments.builder()
            .componentFragment(component)
            .build()
            .inject(this)

        if (savedInstanceState != null) {
            editFragmentView.configurationChange()
        } else {
            argumentsFromMain = EditFragmentArgs.fromBundle(requireArguments())
            editFragmentView.setArgumentsFromMain(argumentsFromMain.todoitem)
        }

        editFragmentView.init()

        return binding.root
    }

}