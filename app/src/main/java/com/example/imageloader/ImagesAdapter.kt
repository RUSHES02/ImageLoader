package com.example.imageloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloader.databinding.ImageHolderBinding
import com.example.imageloader.modals.Images
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesAdapter(private val context: Context): RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    private val differCallBack = object: DiffUtil.ItemCallback<Images>() {
        override fun areItemsTheSame(oldItem: Images, newItem: Images): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Images, newItem: Images): Boolean {
            return oldItem == newItem
        }
    }


    private val differ = AsyncListDiffer(this,differCallBack)

    fun saveData( dataResponse: List<Images>){
        differ.submitList(dataResponse)
        Log.d("in adapter", "${differ.currentList}")

    }


    inner class ImagesViewHolder(val binding : ImageHolderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Images) {
            binding.imageView.tag = image.urls.small
            binding.imageView.setImageDrawable(null)
            CoroutineScope(Dispatchers.Main).launch {
                Loader.loadImageIntoImageView(binding.imageView, image.urls.small)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        Log.d("adapter", "initialized")
       return ImagesViewHolder(ImageHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }
}