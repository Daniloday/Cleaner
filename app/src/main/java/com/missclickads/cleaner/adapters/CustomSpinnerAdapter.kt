package com.missclickads.cleaner.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.missclickads.cleaner.R


class CustomSpinnerAdapter(
     context: Context,textViewResourceId: Int,
    objects: List<String?>?
) : ArrayAdapter<String?>(context!!, textViewResourceId, objects!!) {
    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val row: View = inflater.inflate(R.layout.item_spinner, parent, false)
        val label = row.findViewById<TextView>(R.id.sort)

        return row
    }
}