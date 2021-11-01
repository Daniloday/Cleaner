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
import android.widget.AdapterView

import android.widget.Spinner
import com.missclickads.cleaner.adapters.CustomSpinnerAdapter


import android.widget.ArrayAdapter
import com.missclickads.cleaner.R


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
        val selectedData = mutableListOf<FileModel>()

        val data = getData()

//        when(binding.spinner.selectedItem.toString()){
//            "Max size" -> {
//                data.sortBy { it.size }
//                data.reverse()
//            }
//            "Min size" -> { data.sortBy { it.size } }
//            "Date" -> {data.sortBy { it.title }} // todo change on date
//        }
        data.forEach {
            adapter.add(FileItem(it){ file, selected ->
                if(selected) selectedData.add(file)
                else selectedData.remove(file)
            })
        }


        binding.recycler.addItemDecoration(DividerItemDecoration(activity as MainActivity, DividerItemDecoration.VERTICAL))
        binding.recycler.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.recycler.adapter = adapter

        binding.submitBtn.setOnClickListener {
            Log.e("Files", selectedData.toString())
            phoneData.deleteFiles(selectedData)
            //todo optimization process
            (activity as MainActivity).back = true
            requireActivity().onBackPressed()
        }
        binding.checkBtn.setOnClickListener {
            selectAll(adapter)
        }


//        // Подключаем свой шаблон с значками
//        // Подключаем свой шаблон с значками
//        val sortTypes = listOf("Max size", "Min size", "Date")
//
//        // Подключаем свой шаблон с разными значками
//
//        val adapterSpinner = CustomSpinnerAdapter(
//            activity as MainActivity,
//            R.id.sort, sortTypes
//        )
//
//        // Вызываем адаптер
//
//        // Вызываем адаптер
//        binding.spinner.adapter = adapterSpinner
//        binding.spinner.setPromptId(R.string.phone_booster)
////        binding.spinner.setSelection(2, true)
//
//        binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?, view: View?,
//                pos: Int, id: Long
//            ) {
//            }
//
//            override fun onNothingSelected(arg0: AdapterView<*>?) {}
//        })

    }

    private fun selectAll(adapter: GroupAdapter<GroupieViewHolder>){
        (0 until adapter.itemCount).forEach {
            Log.e("Files", adapter.getItem(it).toString())
            (adapter.getItem(it) as FileItem).selectItem()
        }
    }

    private fun getData(): MutableList<FileModel> = when (filesType) {
            "Video" -> phoneData.getVideos()
            "Audio" -> phoneData.getAudios()
            "Images" -> phoneData.getImages()
            "Documents" -> phoneData.getDocs()
            else -> phoneData.getVideos()
        }


    companion object{
        fun newInstance(param : String) =
             Bundle().apply {
                putString(ARG_PARAM, param)
            }

    }
}