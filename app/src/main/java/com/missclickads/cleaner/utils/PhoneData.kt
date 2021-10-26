package com.missclickads.cleaner.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs

import android.util.DisplayMetrics
import kotlin.math.ceil
import android.provider.MediaStore
import androidx.core.content.ContentResolverCompat.query

import com.missclickads.cleaner.models.VideoModel
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class PhoneData(val context: Context) {

    private val appsCount = 6
    val cpuBeforeOpt = (340..490).random() / 10.0
    val cpuAfterOpt = ((cpuBeforeOpt - 9.0) * 10).roundToInt() / 10.0
    val appMemories = mutableListOf<Int>()

    val junkCleanerOpt = (230..320).random()

    init{
        for (i in 0 until appsCount){
            appMemories.add((10..30).random())
        }
    }

    //phone booster
    fun getMemory(): List<Any> {
        val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        val totalMemory = ceil((memInfo.totalMem / (1024 * 1024)).toDouble() / 1000).toInt()
            .toDouble()
        val availMemory = (memInfo.availMem / (1024 * 1024)).toDouble()
        val usedMemory = (totalMemory * 1000 - availMemory).toInt()
        val percent = (usedMemory / (totalMemory * 10)).roundToInt()
        return listOf(usedMemory, totalMemory, percent)
    }

    fun getStorage(): Pair<Int, Double> {
        val iPath: File = Environment.getDataDirectory()
        val iStat = StatFs(iPath.path)
        val iBlockSize = iStat.blockSizeLong
        val iAvailableBlocks = iStat.availableBlocksLong
        val iTotalBlocks = iStat.blockCountLong
        val iAvailableSpace = ((iAvailableBlocks * iBlockSize) / (1024.0 * 1024)).toInt()
        var iTotalSpace = (iTotalBlocks * iBlockSize) / (1024.0 * 1024 * 1024)
        iTotalSpace = (iTotalSpace * 10.0).roundToInt() / 10.0
        val used = (iTotalSpace * 1000 - iAvailableSpace).toInt()
        return Pair(used,iTotalSpace)

    }

    // battery opt
    fun getBatteryValue() : List<Int> {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val value = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        val batteryHours = (value * 1.2 * 4 / 60).toInt()
        val batteryMinutes = (value * 1.2 * 4 % 60).toInt()
        return listOf(value, batteryHours, batteryMinutes)
    }



    //CPU Cooler

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getAppImages(): MutableList<Drawable>{
        //telephone's apps info
        val appImages = mutableListOf<Drawable>()
        val pm: PackageManager = context.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        var image = 0
        for (i in packages.indices) {
            val ai = pm.getApplicationInfo(packages[i].packageName, 0)
            if (ai.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                continue
            }
            val res: Resources = pm.getResourcesForApplication(packages[i])
            val config: Configuration = res.getConfiguration()
            val originalConfig = Configuration(config)
            config.densityDpi = DisplayMetrics.DENSITY_XHIGH
            val dm: DisplayMetrics = res.getDisplayMetrics()
            res.updateConfiguration(config, dm)
            lateinit var appIcon: Drawable
            try {
                appIcon = res.getDrawable(packages[i].icon)
            }
            catch (e: java.lang.Exception){
                continue
            }
            res.updateConfiguration(originalConfig, dm)
            appImages.add(appIcon)
            image +=1
            if (image == appsCount) break
        }
        return appImages
    }

    fun getFileManagerData(){
        val videos = getVideos()
        for (video in videos){
            println(video.title)
            println(video.size)
        }
    }

    private fun getVideos(): MutableList<VideoModel> {

        val videoList = mutableListOf<VideoModel>()

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

// Show only videos that are at least 5 minutes in duration.
        val selection = "${MediaStore.Video.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES).toString()
        )

// Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        val query = context.getContentResolver().query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                println("pri")
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList.add(VideoModel(uri = contentUri, title = name,size = size.toString()))
            }
        }



        return videoList
    }

}