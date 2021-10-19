package com.missclickads.cleaner.ui.batteryoptimizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.missclickads.cleaner.R

class BatteryOptimizerFragment : Fragment() {

    private lateinit var batteryOptimizerViewModel: BatteryOptimizerViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        batteryOptimizerViewModel =
                ViewModelProvider(this).get(BatteryOptimizerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_battery_optimizer, container, false)
        val textView: TextView = root.findViewById(R.id.text_battery_optimizer)
        batteryOptimizerViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}