package com.missclickads.cleaner.ui.exit

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.FragmentExitDialogBinding
import com.missclickads.cleaner.databinding.FragmentRateDialogBinding
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.utils.OptimizeDataSaver
import org.koin.android.ext.android.inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExitDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExitDialogFragment : DialogFragment() {

    private val optimizeDataSaver : OptimizeDataSaver by inject()
    private var _binding: FragmentExitDialogBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }
        _binding = FragmentExitDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!optimizeDataSaver.dataSaver.junkCleaner){
            binding.textNotOptimized.text = getString(R.string.junk_cleaner)
            binding.btnOptimize.setOnClickListener {
                (activity as MainActivity).callbackFromDialog(OptimizeType.JUNK_CLEANER)
                dismiss()
            }
        }

        if(!optimizeDataSaver.dataSaver.cpuCooler){
            binding.textNotOptimized.text = getString(R.string.cpu_cooler)
            binding.btnOptimize.setOnClickListener {
                (activity as MainActivity).callbackFromDialog(OptimizeType.CPU_COOLER)
                dismiss()
            }
        }

        if(!optimizeDataSaver.dataSaver.batteryOptimizer){
            binding.textNotOptimized.text = getString(R.string.battery_optimizer)
            binding.btnOptimize.setOnClickListener {
                (activity as MainActivity).callbackFromDialog(OptimizeType.BATTERY_OPTIMIZER)
                dismiss()
            }
        }

        if(!optimizeDataSaver.dataSaver.phoneBooster){
            binding.textNotOptimized.text = getString(R.string.phone_booster)
            binding.btnOptimize.setOnClickListener {
                (activity as MainActivity).callbackFromDialog(OptimizeType.PHONE_BOOSTER)
                dismiss()
            }
        }


        binding.textQuit.setOnClickListener {
            (activity as MainActivity).finish()
        }
    }


}