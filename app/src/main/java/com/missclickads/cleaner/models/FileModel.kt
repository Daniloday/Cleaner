package com.missclickads.cleaner.models

import android.graphics.Bitmap
import android.net.Uri

data class FileModel(
    val id : Int = 0,
    val title : String,
    val size : String,
    val image : Bitmap? = null,
    val path : String,
    val uri : Uri? = null
)
