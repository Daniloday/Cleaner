package com.missclickads.cleaner.ui.cpucooler

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.imageview.ShapeableImageView
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentCpuCoolerBinding
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.utils.OptimizeDataSaver
import com.missclickads.cleaner.utils.PhoneData
import org.koin.android.ext.android.inject

class CpuCoolerFragment : BaseFragment<CpuCoolerViewModel>() {

    override val viewModel : CpuCoolerViewModel by viewModels()
    private var _binding: FragmentCpuCoolerBinding? = null
    private val phoneData : PhoneData by inject()
    private val optimizeDataSaver : OptimizeDataSaver by inject()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCpuCoolerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi(){
        if (optimizeDataSaver.dataSaver.cpuCooler) viewModel.endOptimization()

        val appImageViewList = listOf(binding.appImage1,binding.appImage2,binding.appImage3,binding.appImage4,
            binding.appImage5,binding.appImage6,)
        val appMemoriesTextView = listOf(binding.appMb1,binding.appMb2,binding.appMb3,
            binding.appMb4,binding.appMb5,binding.appMb6)
        val appImages = phoneData.getAppImages()
        for(i in appImageViewList.indices){
            appImageViewList[i].setImageDrawable(appImages[i % appImages.size])
        }
        for (i in appMemoriesTextView.indices){
            appMemoriesTextView[i].text = "${phoneData.appMemories[i]} mb"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun notOptimized() {
        Log.e("CpuCooler", "notOptimized")

        binding.shapeableImageView.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_cpu_high))
        binding.temperature.text = "${phoneData.cpuBeforeOpt} °C"
        binding.status.text = getString(R.string.overheated)
        binding.optimizeBtn.text = getString(R.string.optimize_btn)
        binding.optimizeBtn.setOnClickListener {
            viewModel.startOptimization()
        }
        binding.applicationsClass.text = getString(
            R.string.applications_class_are_causing_problem_hit_cool_down)
        binding.appsLinear.visibility = View.VISIBLE

    }

    override fun optimization() {
        Log.e("CpuCooler", "optimization")
        viewModel.endOptimization()
    }

    override fun optimized() {
        Log.e("CpuCooler", "optimized")

        optimizeDataSaver.saveOptimization(type = OptimizeType.CPU_COOLER)

        binding.shapeableImageView.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity, R.drawable.ic_cpu_low))
        binding.temperature.text = "${phoneData.cpuAfterOpt} °C"
        binding.status.text = getString(R.string.normal)
        binding.optimizeBtn.text = getString(R.string.optimized_btn)
        binding.optimizeBtn.setOnClickListener {
            showToast(fragmentName = getString(R.string.cpu_cooler))
        }
        binding.applicationsClass.text = getString(
            R.string.cpu_good)
        binding.appsLinear.visibility = View.INVISIBLE
    }

    override fun error() {
        Log.e("CpuCooler", "error")
    }
}