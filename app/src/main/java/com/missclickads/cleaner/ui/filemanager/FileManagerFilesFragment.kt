package com.missclickads.cleaner.ui.filemanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.databinding.FragmentFileManagerFilesListFragmentBinding
import com.missclickads.cleaner.models.FileModel
import com.missclickads.cleaner.ui.filemanager.items.FileItem
import com.missclickads.cleaner.utils.PhoneData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.ext.android.inject

private const val ARG_PARAM = "param"

class FileManagerFilesFragment : Fragment() {
    val viewModel : FileManagerViewModel by viewModels()
    private var _binding: FragmentFileManagerFilesListFragmentBinding? = null
    private val binding get() = _binding!!

    private val phoneData : PhoneData by inject()
    private var filesType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filesType = it.getString(ARG_PARAM)
        }
    }

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
        val adapter = GroupAdapter<GroupieViewHolder>()

        val data = getData()
        data.forEach {
            adapter.add(FileItem(it))
        }
        binding.recycler.addItemDecoration(DividerItemDecoration(activity as MainActivity, DividerItemDecoration.VERTICAL))
        binding.recycler.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.recycler.adapter = adapter

        binding.checkBtn.setOnClickListener {
            selectAll(adapter)
        }

//        phoneData.getFileManagerData()
    }

    private fun selectAll(adapter: GroupAdapter<GroupieViewHolder>){
        (0 until adapter.itemCount).forEach {
            Log.e("Files", adapter.getItem(it).toString())
            (adapter.getItem(it) as FileItem).selectItem()
        }
    }

    private fun getData(): MutableList<FileModel> = when (filesType) {
            "Video" -> phoneData.getVideos()
            "Audio" -> phoneData.getVideos()
            "Images" -> phoneData.getImages()
            "Documents" -> phoneData.getVideos()
            else -> phoneData.getVideos()
        }


    companion object{
        fun newInstance(param : String) =
             Bundle().apply {
                putString(ARG_PARAM, param)
            }

    }
}