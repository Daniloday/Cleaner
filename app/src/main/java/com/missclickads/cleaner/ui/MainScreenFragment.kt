package com.missclickads.cleaner.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.missclickads.cleaner.MainActivity
import com.missclickads.cleaner.R
import com.missclickads.cleaner.adapters.ViewPagerAdapter
import com.missclickads.cleaner.ui.batteryoptimizer.FROM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {
    var viewPager : ViewPager2? = null
    var act : MainActivity? = null
    private var to : Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("MainScreen", "OnViewCreated")
        to = arguments?.getInt(FROM)
        act = (activity as MainActivity)
        viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val viewPagerAdapter = ViewPagerAdapter(act!!)
        viewPager!!.adapter = viewPagerAdapter
        viewPager!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                choosePosition(position)
            }
        })
        act!!.setupViewPagerFromFragment(viewPager!!)
        if(to != null){
            Log.e("MainScreen", to.toString())
            lifecycleScope.launch {
                when(to){
                    R.id.navigation_battery_optimizer -> {
                        delay(10)
                        viewPager?.currentItem = 1
                    }
                    R.id.navigation_phone_booster -> {
                        delay(10)
                        viewPager?.currentItem = 0
                    }
                    R.id.navigation_junk_cleaner -> {
                        delay(10)
                        viewPager?.currentItem = 3
                    }
                    R.id.navigation_cpu_cooler -> {
                        delay(10)
                        viewPager?.currentItem = 2
                    }
                    R.id.navigation_file_manager -> {
                        delay(10)
                        viewPager?.currentItem = 4
                    }
                }
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        Log.e("MainScreen", "OnResume")
//        act?.navigationView?.visibility = View.VISIBLE
//        viewPager?.let { act?.setupViewPagerFromFragment(it) }
//    }

    fun choosePosition(position: Int){
        when(position){
            0 -> {
                act!!.navigationView?.menu?.findItem(R.id.navigation_phone_booster)?.isChecked = true
            }//navController.navigate(R.id.navigation_phone_booster)
            1 -> {
                //navController.navigate(R.id.navigation_battery_saver)
                act!!.navigationView?.menu?.findItem(R.id.navigation_battery_optimizer)?.isChecked = true
            }
            2 -> {
                // navController.navigate(R.id.navigation_optimizer)
                act!!.navigationView?.menu?.findItem(R.id.navigation_cpu_cooler)?.isChecked = true
            }
            3 -> {
                //navController.navigate(R.id.navigation_junk_cleaner)
                act!!.navigationView?.menu?.findItem(R.id.navigation_junk_cleaner)?.isChecked = true
            }
            4 -> {
                act!!.navigationView?.menu?.findItem(R.id.navigation_file_manager)?.isChecked = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("MainScreen", "OnResume")
        //act?.navigationView?.visibility = View.VISIBLE
        viewPager?.let { act?.setupViewPagerFromFragment(it) }
    }
}