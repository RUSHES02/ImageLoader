package com.example.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.imageloader.caching.DiskCache
import com.example.imageloader.caching.MemoryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object Loader {
    private val memoryCache = MemoryCache()

    suspend fun loadImage(context: Context, url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            // Check memory cache
            var bitmap = memoryCache.getBitmap(url)
            if (bitmap == null) {
                // Check disk cache
                bitmap = DiskCache.getBitmapFromCache(context, url)
                if (bitmap == null) {
                    try {
                        val connection = URL(url).openConnection() as HttpURLConnection
                        connection.doInput = true
                        connection.connect()
                        val input: InputStream = connection.inputStream
                        bitmap = BitmapFactory.decodeStream(input)
                        if (bitmap != null) {
                            // Cache the bitmap
                            memoryCache.putBitmap(url, bitmap)
                            DiskCache.saveBitmapToCache(context, url, bitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    memoryCache.putBitmap(url, bitmap)
                }
            }
            bitmap
        }
    }

    suspend fun loadImageIntoImageView(context: Context, url: String, imageView: ImageView) {
        val bitmap = loadImage(context, url)
        withContext(Dispatchers.Main) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
            } else {
                imageView.setImageResource(R.drawable.ic_error_outline_24) // Placeholder image
            }
        }
    }
}