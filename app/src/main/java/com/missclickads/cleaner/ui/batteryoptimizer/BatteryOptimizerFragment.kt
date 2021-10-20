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
import com.missclickads.cleaner.databinding.FragmentBatteryOptimizerBinding

class BatteryOptimizerFragment : Fragment() {

    private lateinit var batteryOptimizerViewModel: BatteryOptimizerViewModel
    private var _binding: FragmentBatteryOptimizerBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        batteryOptimizerViewModel =
                ViewModelProvider(this).get(BatteryOptimizerViewModel::class.java)
        _binding = FragmentBatteryOptimizerBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}