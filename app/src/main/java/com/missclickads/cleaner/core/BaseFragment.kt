package com.missclickads.cleaner.core

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewStates.observe(viewLifecycleOwner){ state ->
            when(state) {
                is BaseUiState.NotOptimize -> notOptimized()
                is BaseUiState.Optimization -> optimization()
                is BaseUiState.Optimized -> optimized()
                is BaseUiState.Error -> error()
            }
        }
    }

    abstract fun notOptimized()

    abstract fun optimization ()

    abstract fun optimized ()

    abstract fun error()
}