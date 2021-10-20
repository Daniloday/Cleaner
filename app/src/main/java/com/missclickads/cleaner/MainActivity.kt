package com.missclickads.cleaner

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.missclickads.cleaner.utils.OptimizeData
import com.missclickads.cleaner.utils.OptimizeDataSaver
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var dataSaver: OptimizeDataSaver
    private lateinit var optimizeData : OptimizeData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.itemIconTintList = null

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_phone_booster, R.id.navigation_cpu_cooler,  R.id.navigation_battery_optimizer,R.id.navigation_junk_cleaner,R.id.navigation_file_manager))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        dataSaver = OptimizeDataSaver(this)
        optimizeData = dataSaver.getData()

    }

}
