package com.example.imageloader.modals

data class UnsplashResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<Images>
)

data class Images(
    val id: String,
    val description: String?,
    val urls: Urls
)

data class Urls(
    val regular: String,
    val small: String,
    val thumb: String
)