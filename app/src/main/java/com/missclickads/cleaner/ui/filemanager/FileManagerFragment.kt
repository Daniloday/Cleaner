package com.missclickads.cleaner.ui.filemanager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.missclickads.cleaner.R

class FileManagerFragment : Fragment() {

    companion object {
        fun newInstance() = FileManagerFragment()
    }

    private lateinit var viewModel: FileManagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file_manager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FileManagerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}