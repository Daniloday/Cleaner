package com.missclickads.cleaner.ui.cpucooler

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.imageview.ShapeableImageView
import com.missclickads.cleaner.R
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentCpuCoolerBinding
import com.missclickads.cleaner.utils.OptimizeDataSaver
import com.missclickads.cleaner.utils.PhoneData
import org.koin.android.ext.android.inject

class CpuCoolerFragment : BaseFragment<CpuCoolerViewModel>() {

    override val viewModel : CpuCoolerViewModel by viewModels()
    private var _binding: FragmentCpuCoolerBinding? = null
    private val phoneData : PhoneData by inject()
    private val optimizeDataSaver : OptimizeDataSaver by inject()
    lateinit var appImageViewList : List<ShapeableImageView>
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

    private fun initUi(){
        //todo buttons, etc
        appImageViewList = listOf(binding.appImage1,binding.appImage2,binding.appImage3,binding.appImage4,
            binding.appImage5,binding.appImage6,)
        val appImages = phoneData.getAppImages()
        for(i in appImageViewList.indices){
            appImageViewList[i].setImageDrawable(appImages[i % appImages.size])
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun notOptimized() {
        Log.e("CpuCooler", "notOptimized")

    }

    override fun optimization() {
        Log.e("CpuCooler", "optimization")
    }

    override fun optimized() {
        Log.e("CpuCooler", "optimized")
    }

    override fun error() {
        Log.e("CpuCooler", "error")
    }
}