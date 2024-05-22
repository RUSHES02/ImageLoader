package com.example.imageloader.caching

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object DiskCache {

    private const val DISK_CACHE_DIR = "images_cache"

    fun saveBitmapToCache(context: Context, key: String, bitmap: Bitmap) {
        val cacheDir = File(context.cacheDir, DISK_CACHE_DIR)
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        val file = File(cacheDir, key.hashCode().toString())
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getBitmapFromCache(context: Context, key: String): Bitmap? {
        val cacheDir = File(context.cacheDir, DISK_CACHE_DIR)
        val file = File(cacheDir, key.hashCode().toString())
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.path)
        } else {
            null
        }
    }
}
