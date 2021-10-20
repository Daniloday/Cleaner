package com.missclickads.cleaner.ui.batteryoptimizer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentBatteryOptimizerBinding
import com.missclickads.cleaner.utils.PhoneData
import org.koin.android.ext.android.inject

class BatteryOptimizerFragment : BaseFragment<BatteryOptimizerViewModel>() {

    override val viewModel : BatteryOptimizerViewModel by viewModels()
    private var _binding: FragmentBatteryOptimizerBinding? = null
    private val phoneData : PhoneData by inject()
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
        println(phoneData.getBatteryValue())
        println(phoneData.getUsedTotalMemory())
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