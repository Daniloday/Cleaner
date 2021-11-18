package com.missclickads.cleaner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.missclickads.cleaner.R
import com.missclickads.cleaner.ui.batteryoptimizer.BatteryOptimizerFragment
import com.missclickads.cleaner.ui.cpucooler.CpuCoolerFragment
import com.missclickads.cleaner.ui.filemanager.FileManagerFragment
import com.missclickads.cleaner.ui.junkcleaner.JunkCleanerFragment
import com.missclickads.cleaner.ui.phonebooster.PhoneBoosterFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                PhoneBoosterFragment()
            }
            1 -> {
                BatteryOptimizerFragment()
            }
            2 -> {
                CpuCoolerFragment()
            }
            3 -> {
                JunkCleanerFragment()
            }
            4 -> {
                FileManagerFragment()
            }
            else -> {
                PhoneBoosterFragment()
            }
        }
    }

}


