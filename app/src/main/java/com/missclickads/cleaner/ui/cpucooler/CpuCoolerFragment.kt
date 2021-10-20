package com.missclickads.cleaner.ui.cpucooler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.missclickads.cleaner.R
import com.missclickads.cleaner.ui.batteryoptimizer.BatteryOptimizerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CpuCoolerFragment : Fragment() {

    private val viewModel : CpuCoolerViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_cpu_cooler, container, false)
        val textView: TextView = root.findViewById(R.id.text_cpu_cooler)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}