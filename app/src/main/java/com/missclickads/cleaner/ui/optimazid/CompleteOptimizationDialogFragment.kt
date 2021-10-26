package com.missclickads.cleaner.ui.optimazid

import android.animation.Animator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.missclickads.cleaner.databinding.CompleteDialogFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CompleteOptimizationDialogFragment : DialogFragment() {

    private var _binding: CompleteDialogFragmentBinding? = null
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
        _binding = CompleteDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnimationListener()
    }

    fun setupAnimationListener(){
        binding.anim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Log.e("Animation:","start")
            }

            override fun onAncimationEnd(animation: Animator?) {
                dismiss()
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.e("Animation:","start");
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Log.e("Animation:","repeat")
            }

        })
    }
}