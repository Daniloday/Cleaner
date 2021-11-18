package com.missclickads.cleaner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.MobileAds
import com.missclickads.cleaner.models.OptimizeData
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.ui.exit.ExitDialogFragment
import com.missclickads.cleaner.ui.phonebooster.PhoneOptimizationDialogFragment
import com.missclickads.cleaner.ui.rate.RateDialogFragment

import com.missclickads.cleaner.utils.OptimizeDataSaver
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity() {

    private val optimizeDataSaver : OptimizeDataSaver by inject()
    lateinit var navController : NavController
    var back = false
    var navigationView: BottomNavigationView? = null
    var viewPager : ViewPager2? = null

    fun setupViewPagerFromFragment(viewPager2: ViewPager2){
        viewPager = viewPager2
        setUpBottomNavWithViewPager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
//        supportActionBar?.hide()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //navController = findNavController(R.id.nav_host_fragment)
        navigationView = navView
        navView.itemIconTintList = null
//
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_phone_booster, R.id.navigation_cpu_cooler,  R.id.navigation_battery_optimizer,R.id.navigation_junk_cleaner,R.id.navigation_file_manager))
////        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        if (optimizeDataSaver.checkShowRate()) rate()

    }

    fun setUpBottomNavWithViewPager(){
        //navigationView?.menu?.findItem(R.id.navigation_phone_booster)?.isEnabled = false
        navigationView?.setOnNavigationItemSelectedListener  {
            //Log.e("NavSetSelected", it.toString())
            when(it.itemId){
                R.id.navigation_battery_optimizer -> viewPager?.currentItem = 1
                R.id.navigation_phone_booster -> viewPager?.currentItem = 0
                R.id.navigation_junk_cleaner -> viewPager?.currentItem = 3
                R.id.navigation_cpu_cooler -> viewPager?.currentItem = 2
                R.id.navigation_file_manager -> viewPager?.currentItem = 4
            }
            false
        }
    }

    fun setUpBottomNavInEndsFragment(callback : (MenuItem) -> (Unit)){
        //navigationView?.menu?.findItem(R.id.navigation_phone_booster)?.isEnabled = false
        navigationView?.setOnNavigationItemSelectedListener  {
            //Log.e("NavSetSelected", it.toString())
//            when(it.itemId){
//                R.id.navigation_battery_optimizer -> viewPager?.currentItem = 1
//                R.id.navigation_phone_booster -> viewPager?.currentItem = 0
//                R.id.navigation_junk_cleaner -> viewPager?.currentItem = 3
//                R.id.navigation_cpu_cooler -> viewPager?.currentItem = 2
//                R.id.navigation_file_manager -> viewPager?.currentItem = 4
//            }
            callback.invoke(it)
            false
        }
    }

    override fun onBackPressed() {
        if(back) {
            super.onBackPressed()
            back = false
        }
        else{
            if(
                optimizeDataSaver.dataSaver.batteryOptimizer
                && optimizeDataSaver.dataSaver.cpuCooler
                && optimizeDataSaver.dataSaver.junkCleaner
                && optimizeDataSaver.dataSaver.phoneBooster
            ) {
                finish()
            }
            else {
                exit()
            }
        }
    }

    private fun exit(){
        ExitDialogFragment().show(supportFragmentManager,"ExitDialogFragment")
    }

    private fun rate(){
        val dialog = RateDialogFragment()
        dialog.show(supportFragmentManager, "RateDialogFragment")
    }

    fun callbackFromDialog(type : OptimizeType){
//        when(type){
//            OptimizeType.PHONE_BOOSTER -> navController.navigate(R.id.navigation_phone_booster)
//            OptimizeType.BATTERY_OPTIMIZER -> navController.navigate(R.id.navigation_battery_optimizer)
//            OptimizeType.CPU_COOLER -> navController.navigate(R.id.navigation_cpu_cooler)
//            OptimizeType.JUNK_CLEANER -> navController.navigate(R.id.navigation_junk_cleaner)
//        }
        when(type){
            OptimizeType.PHONE_BOOSTER -> viewPager?.currentItem = 0
            OptimizeType.BATTERY_OPTIMIZER -> viewPager?.currentItem = 1
            OptimizeType.CPU_COOLER -> viewPager?.currentItem = 2
            OptimizeType.JUNK_CLEANER -> viewPager?.currentItem = 3
        }

    }



}
