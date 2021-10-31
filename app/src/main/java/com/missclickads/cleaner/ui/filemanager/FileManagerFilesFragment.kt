package com.missclickads.cleaner.ui.filemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.missclickads.cleaner.core.BaseFragment
import com.missclickads.cleaner.databinding.FragmentFileManagerBinding
import com.missclickads.cleaner.databinding.FragmentFileManagerFilesListFragmentBinding

class FileManagerFilesFragment : Fragment() {
    val viewModel : FileManagerViewModel by viewModels()
    private var _binding: FragmentFileManagerFilesListFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileManagerFilesListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }


    private fun initUi(){

    }
}