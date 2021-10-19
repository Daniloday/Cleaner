package com.missclickads.cleaner.ui.junkcleaner

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.missclickads.cleaner.R

class JunkCleanerFragment : Fragment() {

    companion object {
        fun newInstance() = JunkCleanerFragment()
    }

    private lateinit var viewModel: JunkCleanerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_junk_cleaner, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JunkCleanerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}