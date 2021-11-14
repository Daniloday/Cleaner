package com.missclickads.cleaner.ui.filemanager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.FileManagerTypesFragmentBinding
import com.missclickads.cleaner.databinding.FragmentFileManagerBinding
import com.missclickads.cleaner.ui.filemanager.items.TypeItem
import com.missclickads.cleaner.utils.PhoneData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.MainCoroutineDispatcher
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt

class FileManagerTypesFragment : Fragment() {

    private var _binding: FileManagerTypesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FileManagerViewModel
    private val phoneData : PhoneData by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FileManagerTypesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        super.onViewCreated(view, savedInstanceState)
        val adapter = GroupAdapter<GroupieViewHolder>()
        val storage = phoneData.getStorage()
        val percent = (storage.first / (storage.second * 1024)*100).roundToInt().toString()
        binding.storageFileManager.text = "${percent}%"

        val item = TypeItem(
            image = R.drawable.ic_icon_video,
            type = getString(R.string.Video),
            memory = phoneData.getVideos(true).second,
            ::nextFragment
        )
        val item2 = TypeItem(
            image = R.drawable.ic_audio_icon,
            type = getString(R.string.Audio),
            memory = phoneData.getAudios().second,
            ::nextFragment
        )
        val item3 = TypeItem(
            image = R.drawable.ic_images_icon,
            type = getString(R.string.Images),
            memory = phoneData.getImages(true).second,
            ::nextFragment
        )
        val item4 = TypeItem(
            image = R.drawable.ic_documents_icon,
            type = getString(R.string.Documents),
            memory = phoneData.getDocs().second,
            ::nextFragment
        )
        adapter.add(item)
        adapter.add(item2)
        adapter.add(item3)
        adapter.add(item4)
        (activity as MainActivity).back = true
        binding.buttonBack.setOnClickListener {
//            (activity as MainActivity).back = true
            requireActivity().onBackPressed()
        }
        binding.recycler.addItemDecoration(DividerItemDecoration(activity as MainActivity, DividerItemDecoration.VERTICAL))
        binding.recycler.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.recycler.adapter = adapter

    }

    private fun nextFragment(type : String){
        findNavController().navigate(R.id.fileManagerFilesFragment, FileManagerFilesFragment.newInstance(type))
    }

}