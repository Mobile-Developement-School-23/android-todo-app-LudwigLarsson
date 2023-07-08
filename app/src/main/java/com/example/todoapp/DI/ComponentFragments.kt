package com.example.todoapp.DI

import android.app.Activity
import android.view.View
import androidx.lifecycle.LifecycleOwner

data class ComponentFragments(val activity: Activity,
                              val rootView: View,
                              val lifecycleOwner: LifecycleOwner
)