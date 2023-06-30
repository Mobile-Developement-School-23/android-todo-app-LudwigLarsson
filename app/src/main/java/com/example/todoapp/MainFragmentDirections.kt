package com.example.todoapp

import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.String

public class MainFragmentDirections private constructor() {
    private data class ActionMainFragmentToNewTaskFragment(
        public val text: String
    ) : NavDirections {
        public override val actionId: Int = R.id.action_mainFragment_to_newTaskFragment

        public override val arguments: Bundle
            get() {
                val result = Bundle()
                result.putString("text", this.text)
                return result
            }
    }

    public companion object {
        public fun actionMainFragmentToNewTaskFragment(text: String): NavDirections =
            ActionMainFragmentToNewTaskFragment(text)
    }
}