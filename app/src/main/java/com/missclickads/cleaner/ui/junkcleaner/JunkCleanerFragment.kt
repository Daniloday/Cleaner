package com.missclickads.cleaner.ui.junkcleaner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.material.imageview.ShapeableImageView
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentJunkCleanerBinding
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.utils.OptimizeDataSaver
import org.koin.android.ext.android.inject

class JunkCleanerFragment : BaseFragment<JunkCleanerViewModel>() {

    override val viewModel : JunkCleanerViewModel by viewModels()
    private var _binding: FragmentJunkCleanerBinding? = null
    private val optimizeDataSaver : OptimizeDataSaver by inject()
    private val binding get() = _binding!!
    private lateinit var circlesList : List<ShapeableImageView>

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
        if (optimizeDataSaver.dataSaver.junkCleaner) viewModel.endOptimization()
        circlesList = listOf(binding.imageCircle1, binding.imageCircle2,
            binding.imageCircle3, binding.imageCircle4)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun notOptimized() {
        Log.e("JunkCleaner", "notOptimized")

        binding.shapeableImageView.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity,
                R.drawable.ic_junk_hard))
        binding.optimizeBtn.text = getString(R.string.optimize_btn)
        binding.optimizeBtn.setOnClickListener {
            viewModel.startOptimization()
        }
        circlesList.forEach {
            it.setImageDrawable(ContextCompat.getDrawable(activity as MainActivity,
                R.drawable.ic_red_circle_junk))
        }
    }

    override fun optimization() {
        Log.e("JunkCleaner", "optimization")
        viewModel.endOptimization()
    }

    override fun optimized() {
        Log.e("JunkCleaner", "optimized")

        optimizeDataSaver.saveOptimization(type = OptimizeType.JUNK_CLEANER)

        binding.shapeableImageView.setImageDrawable(
            ContextCompat.getDrawable(activity as MainActivity,
                R.drawable.ic_junk_cleared))
        binding.optimizeBtn.text = getString(R.string.optimized_btn)
        binding.optimizeBtn.setOnClickListener {
            showToast(fragmentName = getString(R.string.junk_cleaner))
        }
        circlesList.forEach {
            it.setImageDrawable(ContextCompat.getDrawable(activity as MainActivity,
                R.drawable.ic_green_circle_junk))
        }
    }

    override fun error() {
        Log.e("JunkCleaner", "error")
    }
}