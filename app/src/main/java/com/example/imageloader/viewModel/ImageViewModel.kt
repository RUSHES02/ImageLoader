package com.example.imageloader.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageloader.modals.Images
import com.example.imageloader.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {
    private val imagesList = MutableLiveData<List<Images>>()
    val images: LiveData<List<Images>> get() = imagesList

    private var currentPage = 1
    private val perPage = 10 // Set this to a higher number to load more images

    fun fetchImages() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPhotos(currentPage, perPage, "latest")
                if (response.isSuccessful) {
                    val newImages = response.body() ?: emptyList()
                    val currentImages = imagesList.value.orEmpty()
                    imagesList.postValue(currentImages + newImages)
                    currentPage++ // Increment the page for the next load
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
                e.printStackTrace()
            }
        }
    }
}