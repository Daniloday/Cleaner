package com.missclickads.cleaner.ui.batteryoptimizer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.SplashActivity
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentBatteryOptimizerBinding
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.utils.OptimizeDataSaver
import com.missclickads.cleaner.utils.PhoneData
import org.koin.android.ext.android.inject

class BatteryOptimizerFragment : BaseFragment<BatteryOptimizerViewModel>() {

    override val viewModel : BatteryOptimizerViewModel by viewModels()
    private var _binding: FragmentBatteryOptimizerBinding? = null
    private val phoneData : PhoneData by inject()
    private val optimizeDataSaver : OptimizeDataSaver by inject()
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

    @SuppressLint("SetTextI18n")
    private fun initUi(){
        val batteryValue = phoneData.getBatteryValue()
        binding.batteryInfo.text = "$batteryValue %"

        if (batteryValue <= 20)
            binding.imageBattery.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_red))
        if (batteryValue in 21..50)
            binding.imageBattery.setImageDrawable(
                ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_yellow))
        if (batteryValue > 50)
            binding.imageBattery.setImageDrawable(
                ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_green))
        if (optimizeDataSaver.dataSaver.batteryOptimizer) viewModel.endOptimization()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun notOptimized() {
        Log.e("BatteryOptimizer", "notOptimized")
        binding.optimizeBtn.text = getString(R.string.optimize_btn)
        binding.optimizeBtn.setOnClickListener {
            viewModel.startOptimization()
        }
    }

    override fun optimization() {
        Log.e("BatteryOptimizer", "optimization")
        viewModel.endOptimization()
    }

    override fun optimized() {
        Log.e("BatteryOptimizer", "optimized")
        //todo add to string
        optimizeDataSaver.saveOptimization(type = OptimizeType.BATTERY_OPTIMIZER)
        binding.optimizeBtn.text = getString(R.string.optimized_btn)
        binding.optimizeBtn.setOnClickListener {
            showToast(fragmentName = getString(R.string.battery_optimizer))
        }
    }

    override fun error() {
        Log.e("BatteryOptimizer", "error")
    }




}