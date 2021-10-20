package com.missclickads.cleaner.utils

import android.app.ActivityManager
import android.content.Context
import android.os.BatteryManager
import com.missclickads.cleaner.MainActivity
import kotlin.math.ceil

class PhoneData(val context: Context) {

    fun getUsedTotalMemory(): Pair<Int, Int> {
        val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        val totalMemory = ceil((memInfo.totalMem / (1024 * 1024)).toDouble() / 1000).toInt()
        val availMemory = ceil((memInfo.availMem / (1024 * 1024)).toDouble() / 1000).toInt()
        val usedMemory = totalMemory - availMemory
        return Pair(usedMemory, totalMemory)
    }

    fun getBatteryValue() : Int{
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

}