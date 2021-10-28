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
import com.google.android.gms.ads.AdRequest
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.SplashActivity
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentBatteryOptimizerBinding
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.ui.optimazed.CompleteOptimizationDialogFragment
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
        if (optimizeDataSaver.dataSaver.batteryOptimizer) viewModel.endOptimization()

        val batteryValue = phoneData.getBatteryValue()
        binding.batteryInfo.text = "${batteryValue[0]} %"
        if (batteryValue[0] <= 20)
            binding.imageBattery.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_red))
        if (batteryValue[0] in 21..50)
            binding.imageBattery.setImageDrawable(
                ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_yellow))
        if (batteryValue[0] > 50)
            binding.imageBattery.setImageDrawable(
                ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_battery_green))
        binding.batteryTimeInfoHours.text = batteryValue[1].toString()
        binding.batteryTimeInfoMinutes.text = batteryValue[2].toString()
        //ads
        binding.adView.loadAd(AdRequest.Builder().build())
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
        val dialog = BatteryOptimizationDialogFragment{
            viewModel.endOptimization()
        }
        dialog.show(childFragmentManager, "optimization")

    }

    override fun optimized() {
        Log.e("BatteryOptimizer", "optimized")

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