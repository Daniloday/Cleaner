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

class CpuCoolerFragment : Fragment() {

    private lateinit var cpuCoolerViewModel: CpuCoolerViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cpuCoolerViewModel =
                ViewModelProvider(this).get(CpuCoolerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cpu_cooler, container, false)
        val textView: TextView = root.findViewById(R.id.text_cpu_cooler)
        cpuCoolerViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}