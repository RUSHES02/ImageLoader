package com.example.imageloader.caching

import android.graphics.Bitmap
import android.util.LruCache

class MemoryCache {
    private val cache: LruCache<String, Bitmap> = object : LruCache<String, Bitmap>(calculateCacheSize()) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.byteCount / 1024
        }
    }

    private fun calculateCacheSize(): Int {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        return maxMemory / 8
    }

    fun getBitmap(key: String): Bitmap? {
        return cache.get(key)
    }

    fun putBitmap(key: String, bitmap: Bitmap) {
        cache.put(key, bitmap)
    }
}