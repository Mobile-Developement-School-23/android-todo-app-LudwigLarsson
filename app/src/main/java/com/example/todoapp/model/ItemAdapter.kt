package com.example.todoapp.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.todoapp.R

class ItemAdapter(context: Context?, products: ArrayList<String?>?) :
    ArrayAdapter<String?>(context!!, R.layout.item, products!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null)
        }
        return convertView!!
    }
}