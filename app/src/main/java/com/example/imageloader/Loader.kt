package com.example.imageloader

import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.imageloader.caching.DiskCache
import com.example.imageloader.caching.MemoryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object Loader {
    suspend fun loadImageIntoImageView(imageView: ImageView, url: String) {
        withContext(Dispatchers.IO) {
            val memoryCache = MemoryCache()
            val diskCache = DiskCache(imageView.context)

            var bitmap = memoryCache.getBitmap(url)
            if (bitmap == null) {
                bitmap = diskCache.getBitmap(url)
                if (bitmap == null) {
                    try {
                        val connection = URL(url).openConnection()
                        connection.connect()
                        val input = connection.getInputStream()
                        bitmap = BitmapFactory.decodeStream(input)
                        input.close()
                        memoryCache.putBitmap(url, bitmap)
                        diskCache.putBitmap(url, bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    memoryCache.putBitmap(url, bitmap)
                }
            }

            withContext(Dispatchers.Main) {
                if (imageView.tag == url) {
                    imageView.setImageBitmap(bitmap)
                }else{
                    imageView.setImageResource(R.drawable.ic_error_outline_24)
                }
            }
        }
    }
}