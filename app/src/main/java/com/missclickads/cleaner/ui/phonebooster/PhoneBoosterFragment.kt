package com.missclickads.cleaner.ui.phonebooster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import android.util.Log
import androidx.core.content.ContextCompat
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentPhoneBoosterBinding
import com.missclickads.cleaner.models.OptimizeType
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
        if (optimizeDataSaver.dataSaver.phoneBooster) viewModel.endOptimization()
        val memory = phoneData.getMemory()
        val storage = phoneData.getStorage()
        binding.storage.text = storage.first.toString()
        binding.storage2.text = storage.first.toString()
        binding.storageMb.text = "${storage.first} MB / ${storage.second} GB"
        binding.ram.text = "${memory[2]}%"
        binding.ramMb.text = "${memory[0]} MB / ${memory[1]} GB"
        binding.progressBarCircle.progress = memory[2] as Int
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun notOptimized() {
        Log.e("PhoneBooster", "notOptimized")
        binding.progressBarCircle.progressDrawable = ContextCompat.getDrawable(activity as MainActivity,
            R.drawable.progress_bar_circle_red)
        binding.optimizeBtn.text = getString(R.string.optimize_btn)
        binding.optimizeBtn.setOnClickListener {
            viewModel.startOptimization()
        }
    }

    override fun optimization() {
        val dialog = PhoneOptimizationDialogFragment()
        dialog.show(childFragmentManager, "optimization")
        Log.e("PhoneBooster", "optimization")
        viewModel.endOptimization()
    }

    override fun optimized() {
        Log.e("PhoneBooster", "optimized")
        optimizeDataSaver.saveOptimization(type = OptimizeType.PHONE_BOOSTER)
        binding.progressBarCircle.progressDrawable = ContextCompat.getDrawable(activity as MainActivity,
            R.drawable.progress_bar_circle_blue)
        binding.optimizeBtn.text = getString(R.string.optimized_btn)
        binding.optimizeBtn.setOnClickListener {
            showToast(fragmentName = getString(R.string.phone_booster))
        }
    }

    override fun error() {
        Log.e("PhoneBooster", "error")
    }
}