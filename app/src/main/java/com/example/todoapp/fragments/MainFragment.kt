package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import com.example.todoapp.DI.ComponentFragments
import com.example.todoapp.DI.DaggerMainComponentFragments
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    @Inject
    lateinit var mainFragmentView: MainFragmentView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val component = ComponentFragments (
            requireActivity(),
            binding.root,
            viewLifecycleOwner
        )

        DaggerMainComponentFragments
            .builder()
            .componentFragment(component)
            .build()
            .inject(this)

        lifecycle.coroutineScope.launch {
            mainFragmentView.init()
        }
        return binding.root
    }
}