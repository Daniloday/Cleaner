package com.missclickads.cleaner.ui.filemanager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.missclickads.cleaner.R

class FileManagerTypesFragment : Fragment() {

    companion object {
        fun newInstance() = FileManagerTypesFragment()
    }

    private lateinit var viewModel: FileManagerTypesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.file_manager_types_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FileManagerTypesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}