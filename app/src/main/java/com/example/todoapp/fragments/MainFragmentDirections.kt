package com.example.todoapp.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.todoapp.R
import kotlin.Int
import kotlin.String

 class MainFragmentDirections private constructor() {
    private data class ActionMainFragmentToNewTaskFragment(
        val text: String
    ) : NavDirections {
        override val actionId: Int = R.id.action_mainFragment_to_newTaskFragment

        override val arguments: Bundle
            get() {
                val result = Bundle()
                result.putString("text", this.text)
                return result
            }
    }

    companion object {
        fun actionMainFragmentToNewTaskFragment(text: String): NavDirections =
            ActionMainFragmentToNewTaskFragment(text)
    }
}