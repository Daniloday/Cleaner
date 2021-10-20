package com.missclickads.cleaner.ui.filemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import android.util.Log
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentFileManagerBinding

class FileManagerFragment : BaseFragment<FileManagerViewModel>() {

    override val viewModel : FileManagerViewModel by viewModels()
    private var _binding: FragmentFileManagerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileManagerBinding.inflate(layoutInflater)
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
        Log.e("FileManager", "notOptimized")
    }

    override fun optimization() {
        Log.e("FileManager", "optimization")
    }

    override fun optimized() {
        Log.e("FileManager", "optimized")
    }

    override fun error() {
        Log.e("FileManager", "error")
    }
}