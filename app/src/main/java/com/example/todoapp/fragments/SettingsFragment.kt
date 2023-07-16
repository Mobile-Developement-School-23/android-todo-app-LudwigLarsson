package com.example.todoapp.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
            Configuration.UI_MODE_NIGHT_YES -> binding.themeName.text = "Тёмная"
            Configuration.UI_MODE_NIGHT_NO -> binding.themeName.text = "Светлая"
        }

        binding.theme.setOnClickListener {
            BottomSheetFragment(object : ThemeInterface{
                override fun changeTheme() {

                }

            }).show(parentFragmentManager, "change_task")
        }

        return binding.root
    }

    interface ThemeInterface {
        fun changeTheme()
    }
}