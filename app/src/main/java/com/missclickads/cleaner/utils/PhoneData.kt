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
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.MediaStore
import android.util.DisplayMetrics
import com.missclickads.cleaner.models.FileModel
import kotlinx.coroutines.*
import java.io.File
import kotlin.math.ceil
import kotlin.math.roundToInt


class PhoneData(val context: Context) {

    private val appsCount = 6
    val cpuBeforeOpt = (340..490).random() / 10.0
    val cpuAfterOpt = ((cpuBeforeOpt - 9.0) * 10).roundToInt() / 10.0
    val junkCleaner = (230..320).random()
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



    fun getVideos(): MutableList<FileModel> {

        val contentResolver: ContentResolver = context.contentResolver
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)
        val videos = mutableListOf<FileModel>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                val size =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))

                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))

                val media = MediaMetadataRetriever()
                media.setDataSource(path)
                val extractedImage = media.frameAtTime
                val videoUri: Uri = ContentUris
                    .withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID))
                            .toLong()
                    )
                val video = FileModel(
                    title = title,
                    size = getCorrectSize(size),
                    image = extractedImage,
                    path = path,
                    uri = videoUri
                )
                println(video)
                videos.add(video)

            } while (cursor.moveToNext())
        }

        return videos

    }

    fun getImages(): MutableList<FileModel> {

        val contentResolver: ContentResolver = context.contentResolver
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)
        val images = mutableListOf<FileModel>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                val size =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.SIZE))

                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))

                val imageUri: Uri = ContentUris
                    .withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID))
                            .toLong()
                    )

                val im = BitmapFactory.decodeFile(path)


                val image = FileModel(
                    title = title,
                    size = getCorrectSize(size),
                    image = im,
                    path = path,
                    uri = imageUri
                )
                images.add(image)

            } while (cursor.moveToNext())
        }

        return images

    }

    fun getAudios(): MutableList<FileModel> {

        val contentResolver: ContentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver
            .query(uri, null, null, null, null)
        val audios = mutableListOf<FileModel>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                val size =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val uri: Uri = ContentUris
                    .withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID))
                            .toLong()
                    )
                val audio = FileModel(
                    title = title,
                    size = getCorrectSize(size),
                    path = path,
                    uri = uri
                )
                audios.add(audio)

            } while (cursor.moveToNext())
        }

        return audios

    }

    fun getDocs(): MutableList<FileModel> {

        val contentResolver: ContentResolver = context.contentResolver
        val uri = MediaStore.Files.getContentUri("external")
        val cursor = contentResolver
            .query(uri, null, "_data LIKE '%.pdf'", null, "_id DESC")
        val docs = mutableListOf<FileModel>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME))
                val size =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE))
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA))
                val uri: Uri = ContentUris
                    .withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID))
                            .toLong()
                    )
                val doc = FileModel(
                    title = title,
                    size = getCorrectSize(size),
                    path = path,
                    uri = uri
                )
                docs.add(doc)

            } while (cursor.moveToNext())
        }

        return docs

    }

    fun deleteFiles(files : List<FileModel>){
        for (file in files){
            val pat = file.path
            val fDelete= File(pat)
            if (fDelete.exists()) {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        val contentResolver: ContentResolver = context.getContentResolver()
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            println("R r r")
                            MediaStore.createDeleteRequest(contentResolver, listOf(file.uri))
                        }
                        else{
                            println("k r r")
                            contentResolver.delete(file.uri!!, null, null)
                        }

                    }
                }

            }

        }
    }

    private fun getCorrectSize(size : String) =
        (((size.toInt() / (1024.0 * 1024.0)) * 10).roundToInt() / 10.0).toString() + " mb"


}