package com.missclickads.cleaner.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.missclickads.cleaner.models.OptimizeData
import com.missclickads.cleaner.models.OptimizeType
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val S_PHONE_BOOSTER = "PHONE_BOOSTER"
const val S_BATTERY_OPTIMIZER = "BATTERY_OPTIMIZER"
const val S_CPU_COOLER = "CPU_COOLER "
const val S_JUNK_CLEANER = "JUNK_CLEANER"

const val S_PREF_NAME = "shpr"
const val DATA_PATTERN = "dd-MM-yyyy HH:mm:ss"

const val S_FIRST_LAUNCH = "FIRST_LAUNCH"
const val BASE_VALUE = "First"

class OptimizeDataSaver(context: Context) {

    private var sPrefs : SharedPreferences = context.getSharedPreferences(S_PREF_NAME, Context.MODE_PRIVATE)
    private val dateFormat: DateFormat = SimpleDateFormat(DATA_PATTERN)
    private val formatter = SimpleDateFormat(DATA_PATTERN)
    var dataSaver : OptimizeData

    init{
        dataSaver = getData()
    }

    @SuppressLint("SimpleDateFormat")
    fun saveOptimization(type: OptimizeType){
        val date = Date()
        when(type){
            OptimizeType.PHONE_BOOSTER ->
                sPrefs.edit().putString(S_PHONE_BOOSTER, dateFormat.format(date)).apply()
            OptimizeType.BATTERY_OPTIMIZER ->
                sPrefs.edit().putString(S_BATTERY_OPTIMIZER, dateFormat.format(date)).apply()
            OptimizeType.CPU_COOLER ->
                sPrefs.edit().putString(S_CPU_COOLER, dateFormat.format(date)).apply()
            OptimizeType.JUNK_CLEANER ->
                sPrefs.edit().putString(S_JUNK_CLEANER, dateFormat.format(date)).apply()
        }
        dataSaver = getData()
    }

    fun checkShowRate(): Boolean {
        val date = Date()
        val oneDay = 24 * 60 * 60 * 1000
        val firstLaunch = sPrefs.getString(S_FIRST_LAUNCH, BASE_VALUE)
        return if (firstLaunch == BASE_VALUE){
            sPrefs.edit().putString(S_FIRST_LAUNCH, dateFormat.format(date)).apply()
            false
        } else {
            date.time - formatter.parse(firstLaunch!!)!!.time > oneDay
        }
    }

    fun getData(): OptimizeData {
        val timeUpdateStatus = 15 * 60 * 1000
        val phoneBooster = getDataFormat(S_PHONE_BOOSTER)
        val batteryOptimizer = getDataFormat(S_BATTERY_OPTIMIZER)
        val cpuCooler = getDataFormat(S_CPU_COOLER)
        val junkCleaner = getDataFormat(S_JUNK_CLEANER)
        val date = Date()

        return OptimizeData(
            phoneBooster = date.time - phoneBooster.time <= timeUpdateStatus,
            batteryOptimizer = date.time - batteryOptimizer.time <= timeUpdateStatus,
            cpuCooler = date.time - cpuCooler.time <= timeUpdateStatus,
            junkCleaner = date.time - junkCleaner.time <= timeUpdateStatus,
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDataFormat(type : String):Date{
        val dataStr = sPrefs.getString(type,"20-10-2020 12:12:12")
        return formatter.parse(dataStr!!)!!
    }


}



