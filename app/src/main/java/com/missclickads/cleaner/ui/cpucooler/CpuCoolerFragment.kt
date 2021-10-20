package com.missclickads.cleaner.ui.cpucooler

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentCpuCoolerBinding

class CpuCoolerFragment : BaseFragment<CpuCoolerViewModel>() {

    override val viewModel : CpuCoolerViewModel by viewModels()
    private var _binding: FragmentCpuCoolerBinding? = null

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