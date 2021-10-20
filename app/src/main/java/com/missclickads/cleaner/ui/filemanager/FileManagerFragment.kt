package com.missclickads.cleaner.ui.filemanager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.missclickads.cleaner.R
import com.missclickads.cleaner.ui.cpucooler.CpuCoolerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FileManagerFragment : Fragment() {

    private val viewModel : FileManagerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file_manager, container, false)
    }



}