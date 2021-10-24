package com.missclickads.cleaner.ui.phonebooster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import android.util.Log
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentPhoneBoosterBinding
import com.missclickads.cleaner.utils.OptimizeDataSaver
import com.missclickads.cleaner.utils.PhoneData
import org.koin.android.ext.android.inject


class PhoneBoosterFragment : BaseFragment<PhoneBoosterViewModel>() {

    override val viewModel : PhoneBoosterViewModel by viewModels()
    private var _binding: FragmentPhoneBoosterBinding? = null
    private val phoneData : PhoneData by inject()
    private val optimizeDataSaver : OptimizeDataSaver by inject()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneBoosterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi(){
        //todo buttons, etc
        binding.progressBarCircle.progress = 60
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun notOptimized() {
        Log.e("PhoneBooster", "notOptimized")
    }

    override fun optimization() {
        Log.e("PhoneBooster", "optimization")
    }

    override fun optimized() {
        Log.e("PhoneBooster", "optimized")
    }

    override fun error() {
        Log.e("PhoneBooster", "error")
    }
}