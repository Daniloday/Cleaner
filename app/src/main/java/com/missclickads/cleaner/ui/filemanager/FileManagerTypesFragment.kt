package com.missclickads.cleaner.ui.filemanager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.FileManagerTypesFragmentBinding
import com.missclickads.cleaner.databinding.FragmentFileManagerBinding
import com.missclickads.cleaner.ui.filemanager.items.TypeItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.MainCoroutineDispatcher

class FileManagerTypesFragment : Fragment() {

    private var _binding: FileManagerTypesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FileManagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FileManagerTypesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GroupAdapter<GroupieViewHolder>()

        val item = TypeItem(
            image = R.drawable.ic_video_icon,
            type = "Video",
            memory = "268mb"
        )
        val item2 = TypeItem(
            image = R.drawable.ic_audio_icon,
            type = "Audio",
            memory = "268mb"
        )
        val item3 = TypeItem(
            image = R.drawable.ic_images_icon,
            type = "Images",
            memory = "268mb"
        )
        val item4 = TypeItem(
            image = R.drawable.ic_documents_icon,
            type = "Documents",
            memory = "268mb"
        )
        adapter.add(item)
        adapter.add(item2)
        adapter.add(item3)
        adapter.add(item4)

        binding.recycler.addItemDecoration(DividerItemDecoration(activity as MainActivity, DividerItemDecoration.VERTICAL))
        binding.recycler.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.recycler.adapter = adapter
    }

}