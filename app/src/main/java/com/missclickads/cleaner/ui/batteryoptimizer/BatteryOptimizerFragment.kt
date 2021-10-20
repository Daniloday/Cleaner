package com.missclickads.cleaner.ui.batteryoptimizer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.missclickads.cleaner.R
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentBatteryOptimizerBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BatteryOptimizerFragment : BaseFragment<BatteryOptimizerViewModel>() {

    override val viewModel : BatteryOptimizerViewModel by viewModels()
    private var _binding: FragmentBatteryOptimizerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatteryOptimizerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi(){
        //todo buttons, etc
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun notOptimized() {
        Log.e("BatteryOptimizer", "notOptimized")
    }

    override fun optimization() {
        Log.e("BatteryOptimizer", "optimization")
    }

    override fun optimized() {
        Log.e("BatteryOptimizer", "optimized")
    }

    override fun error() {
        Log.e("BatteryOptimizer", "error")
    }
}