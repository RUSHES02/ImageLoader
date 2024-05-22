package com.example.imageloader.caching

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DiskCache(private val context: Context) {

    private val cacheDir: File = context.cacheDir

    fun getBitmap(url: String): Bitmap? {
        val file = File(cacheDir, urlToFileName(url))
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }

    fun putBitmap(url: String, bitmap: Bitmap) {
        val file = File(cacheDir, urlToFileName(url))
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun urlToFileName(url: String): String {
        return url.hashCode().toString()
    }
}
