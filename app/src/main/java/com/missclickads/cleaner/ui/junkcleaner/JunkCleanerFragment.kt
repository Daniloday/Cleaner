package com.missclickads.cleaner.ui.junkcleaner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import android.util.Log
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentJunkCleanerBinding

class JunkCleanerFragment : BaseFragment<JunkCleanerViewModel>() {

    override val viewModel : JunkCleanerViewModel by viewModels()
    private var _binding: FragmentJunkCleanerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJunkCleanerBinding.inflate(layoutInflater)
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
        Log.e("JunkCleaner", "notOptimized")
    }

    override fun optimization() {
        Log.e("JunkCleaner", "optimization")
    }

    override fun optimized() {
        Log.e("JunkCleaner", "optimized")
    }

    override fun error() {
        Log.e("JunkCleaner", "error")
    }
}