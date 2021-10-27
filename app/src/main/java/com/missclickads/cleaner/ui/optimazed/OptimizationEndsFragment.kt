package com.missclickads.cleaner.ui.optimazed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.FragmentJunkCleanerBinding
import com.missclickads.cleaner.databinding.FragmentOptimizationEndBinding
import com.missclickads.cleaner.databinding.JunkCleanerOptimizationFragmentBinding
import com.missclickads.cleaner.ui.filemanager.items.TypeItem
import com.missclickads.cleaner.ui.optimazed.item.OptimizationItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class OptimizationEndsFragment : Fragment() {
    private var _binding: FragmentOptimizationEndBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptimizationEndBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    fun setupRecycler(){
        val adapter = GroupAdapter<GroupieViewHolder>()

        val item = OptimizationItem(
            image = R.drawable.ic_video_icon,
            title = "Video",
        )
        val item2 = OptimizationItem(
            image = R.drawable.ic_video_icon,
            title = "Video",
        )

        adapter.add(item)
        adapter.add(item2)
        binding.recycler.addItemDecoration(DividerItemDecoration(activity as MainActivity, DividerItemDecoration.VERTICAL))
        binding.recycler.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.recycler.adapter = adapter
    }
}