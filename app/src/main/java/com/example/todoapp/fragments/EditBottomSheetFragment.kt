package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.todoapp.R
import com.example.todoapp.databinding.EditFragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditBottomSheetFragment() : BottomSheetDialogFragment() {


    private lateinit var binding: EditFragmentBottomSheetBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditFragmentBottomSheetBinding.inflate(layoutInflater, container, false)

        binding.radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.no -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.low -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                R.id.high -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            dismiss()
        }

        return binding.root
    }
}