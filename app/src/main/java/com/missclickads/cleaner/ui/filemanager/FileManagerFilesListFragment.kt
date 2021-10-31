package com.missclickads.cleaner.ui.filemanager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.missclickads.cleaner.R

class FileManagerFilesListFragment : Fragment() {

    companion object {
        fun newInstance() = FileManagerFilesListFragment()
    }

    private lateinit var viewModelFragment: FileManagerFilesListViewModelFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_file_manager_files_list_fragment,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFragment = ViewModelProvider(this).get(FileManagerFilesListViewModelFragment::class.java)
        // TODO: Use the ViewModel
    }

}