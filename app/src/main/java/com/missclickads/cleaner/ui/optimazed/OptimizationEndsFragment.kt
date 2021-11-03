package com.missclickads.cleaner.ui.optimazed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.FragmentJunkCleanerBinding
import com.missclickads.cleaner.databinding.FragmentOptimizationEndBinding
import com.missclickads.cleaner.databinding.JunkCleanerOptimizationFragmentBinding
import com.missclickads.cleaner.ui.batteryoptimizer.FROM
import com.missclickads.cleaner.ui.filemanager.items.TypeItem
import com.missclickads.cleaner.ui.optimazed.item.OptimizationItem
import com.missclickads.cleaner.utils.OptimizeDataSaver
import com.missclickads.cleaner.utils.PhoneData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.ext.android.inject

class OptimizationEndsFragment : Fragment() {
    private var _binding: FragmentOptimizationEndBinding? = null
    private val binding get() = _binding!!
    private val optimizeDataSaver : OptimizeDataSaver by inject()
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
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setupRecycler(){
        val adapter = GroupAdapter<GroupieViewHolder>()

        val item = OptimizationItem(
            image =
            if(optimizeDataSaver.dataSaver.batteryOptimizer) R.drawable.ic_battery022_icon
            else R.drawable.battery_warning,
            title = "Battery\nSaver",
            setOnClickCallback = {
                findNavController().navigate(R.id.navigation_battery_optimizer, Bundle().apply { putBoolean(FROM,true) })
            }
        )
        val item2 = OptimizationItem(
            image =
            if (optimizeDataSaver.dataSaver.cpuCooler) R.drawable.ic_cpu022_icon
            else R.drawable.cpu_warning,
            title = "CPU\nCooler",
            setOnClickCallback = {
                findNavController().navigate(R.id.navigation_cpu_cooler, Bundle().apply { putBoolean(FROM,true) })
            }
        )
        val item3 = OptimizationItem(
            image =
            if (optimizeDataSaver.dataSaver.junkCleaner) R.drawable.ic_junk022_icon
            else R.drawable.junk_warning,
            title = "Junk\nCleaner",
            setOnClickCallback = {
                findNavController().navigate(R.id.navigation_junk_cleaner, Bundle().apply { putBoolean(FROM,true) })
            }
        )
        val item4 = OptimizationItem(
            image =
            if (optimizeDataSaver.dataSaver.phoneBooster) R.drawable.ic_phone022_icon
            else R.drawable.phone_warning,
            title = "Phone\nBooster",
            setOnClickCallback = {
                findNavController().navigate(R.id.navigation_phone_booster, Bundle().apply { putBoolean(FROM,true) })
            }
        )

        binding.buttonBack.setOnClickListener {
            (activity as MainActivity).back = true
            requireActivity().onBackPressed()
        }
        adapter.add(item)
        adapter.add(item2)
        adapter.add(item3)
        adapter.add(item4)
        binding.recycler.addItemDecoration(DividerItemDecoration(activity as MainActivity, DividerItemDecoration.VERTICAL))
        binding.recycler.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.recycler.adapter = adapter
    }
}