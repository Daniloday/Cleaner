package com.missclickads.cleaner

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.missclickads.cleaner.models.OptimizeData
import com.missclickads.cleaner.models.OptimizeType
import com.missclickads.cleaner.ui.phonebooster.PhoneOptimizationDialogFragment
import com.missclickads.cleaner.ui.rate.RateDialogFragment

import com.missclickads.cleaner.utils.OptimizeDataSaver
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity() {

    private val optimizeDataSaver : OptimizeDataSaver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        supportActionBar?.hide()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.itemIconTintList = null

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_phone_booster, R.id.navigation_cpu_cooler,  R.id.navigation_battery_optimizer,R.id.navigation_junk_cleaner,R.id.navigation_file_manager))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (optimizeDataSaver.checkShowRate()) rate()

    }

    fun rate(){
        val dialog = RateDialogFragment()
        dialog.show(supportFragmentManager, "RateDialogFragment")
    }



}
