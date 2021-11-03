package com.missclickads.cleaner.ui.filemanager

import android.annotation.SuppressLint
import android.content.ContentValues
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
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.missclickads.cleaner.R
import com.missclickads.cleaner.ui.filemanager.items.ChildrenDropDown
import com.missclickads.cleaner.ui.filemanager.items.ParentDropDown
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Section


private const val ARG_PARAM = "param"

class FileManagerFilesFragment : Fragment() {
    val viewModel : FileManagerViewModel by viewModels()
    private var _binding: FragmentFileManagerFilesListFragmentBinding? = null
    private val binding get() = _binding!!
    var mInterstitialAd: InterstitialAd? = null
    private val phoneData : PhoneData by inject()
    private var filesType: String? = null

    private lateinit var adapter : GroupAdapter<GroupieViewHolder>
    private lateinit var data :  MutableList<FileModel>

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
        binding.storageName.text = filesType
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        loadAds(adRequest)
        initUi()
        binding.buttonBack.setOnClickListener {
            (activity as MainActivity).back = true
            requireActivity().onBackPressed()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initUi(){

        adapter = GroupAdapter<GroupieViewHolder>()
        val selectedData = mutableListOf<FileModel>()

        data = getData()

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
            val dialog = FileOptimizationDialogFragment(text2 = getSize(selectedData)) {
                viewModel.endOptimization()
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(activity as MainActivity)
                    println("Ads go!")
                }
            }
            dialog.show(childFragmentManager, "optimization")
//            (activity as MainActivity).back = true
//            requireActivity().onBackPressed()
        }
        binding.checkBtn.setOnClickListener {
            selectAll(adapter)
        }

        initSpinner()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortBySize(min : Boolean){

        println("sorting")
        data.sortBy { it.size }
        if(min) data.reverse()
        adapter.notifyDataSetChanged()
    }


    private fun initSpinner(){
        val adapter = GroupAdapter<GroupieViewHolder>()
        val spinnerParent = ExpandableGroup(ParentDropDown())
            .apply {
                add(Section().apply {
                    add(ChildrenDropDown("Sort by date") { sortBySize(true)

                    })
                })
                add(Section().apply {
                    add(ChildrenDropDown("Sort by max size") { sortBySize(false) })
                })
                add(Section().apply {
                    add(ChildrenDropDown("Sort by min size") { sortBySize(true) })
                })
            }
        adapter.add(spinnerParent)
//        binding.dropdownMenu.addItemDecoration(DividerItemDecoration(activity as MainActivity, DividerItemDecoration.VERTICAL))
        binding.dropdownMenu.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.dropdownMenu.adapter = adapter
    }

    private fun selectAll(adapter: GroupAdapter<GroupieViewHolder>){
        (0 until adapter.itemCount).forEach {
            Log.e("Files", adapter.getItem(it).toString())
            (adapter.getItem(it) as FileItem).selectItem()
        }
    }

    private fun getData(): MutableList<FileModel> = when (filesType) {
            "Video" -> phoneData.getVideos().first
            "Audio" -> phoneData.getAudios().first
            "Images" -> phoneData.getImages().first
            "Documents" -> phoneData.getDocs().first
            else -> phoneData.getVideos().first
        }
    private fun getSize(list: List<FileModel>): String{
        var size = 0.0
        list.forEach{
            size+=(it.size).removeSuffix(" mb").toDouble()
        }
        return size.toString()
    }


    companion object{
        fun newInstance(param : String) =
             Bundle().apply {
                putString(ARG_PARAM, param)
            }

    }

    private fun loadAds(adRequest: AdRequest){
        //ads
        InterstitialAd.load(
            activity as MainActivity,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(ContentValues.TAG, adError?.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(ContentValues.TAG, "Ad was loaded")
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                Log.d(ContentValues.TAG, "Ad was dismissed.")
//                                findNavController().navigate(R.id.optimizationEndsFragment)
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                                Log.d(ContentValues.TAG, "Ad failed to show.")
//                                findNavController().navigate(R.id.optimizationEndsFragment)
                            }


                            override fun onAdShowedFullScreenContent() {
//                                findNavController().navigate(R.id.optimizationEndsFragment)
                                Log.d(ContentValues.TAG, "Ad showed fullscreen content.")
                                mInterstitialAd = null
                            }
                        }
                }
            })
    }
}