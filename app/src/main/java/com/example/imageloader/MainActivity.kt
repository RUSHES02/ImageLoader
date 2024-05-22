package com.example.imageloader

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloader.databinding.ActivityMainBinding
import com.example.imageloader.viewModel.ImageViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewMain.layoutManager =  GridLayoutManager(this, 3)
        val imagesAdapter = ImagesAdapter(this)
        binding.recyclerViewMain.adapter = imagesAdapter

        imageViewModel.images.observe(this, Observer { images ->
//            val a : MutableList<Images> = mutableListOf()
//            a.add(images)
            imagesAdapter.saveData(images)
        })

        // Fetch images
        imageViewModel.fetchImages()

        binding.recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!recyclerView.canScrollVertically(1)) { // Check if we have reached the bottom
                    imageViewModel.fetchImages() // Load more images
                }
            }
        })
    }
}