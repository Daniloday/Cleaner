package com.missclickads.cleaner.ui.phonebooster

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.missclickads.cleaner.databinding.CompleteDialogFragmentBinding
import com.missclickads.cleaner.databinding.PhoneBoosterOptimizationFragmentBinding
import com.missclickads.cleaner.ui.optimazed.CompleteOptimizationDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhoneOptimizationDialogFragment : DialogFragment() {
    private var _binding: PhoneBoosterOptimizationFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (getDialog() != null && getDialog()?.getWindow() != null) {
            getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            getDialog()?.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE);
        }
        _binding = PhoneBoosterOptimizationFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            val animation = ObjectAnimator.ofInt(progressBarCircle, "progress", 0, 100)
            animation.duration = 5 * 1000
            animation.start()
            lifecycleScope.launch{
                delay(5*1000)
                val dialogCompleted = CompleteOptimizationDialogFragment()
                dialogCompleted.show(childFragmentManager, "optimization")

                dismiss()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}