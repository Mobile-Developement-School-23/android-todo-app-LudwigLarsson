package com.example.todoapp

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //var binding: ActivityMainBinding? = null
    var newTaskFragment: NewTaskFragment = NewTaskFragment()
    var mainFragment: MainFragment = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, mainFragment).commit()
        getSupportFragmentManager().beginTransaction().replace(R.id.newTaskFragment, newTaskFragment).commit()
    }
}

