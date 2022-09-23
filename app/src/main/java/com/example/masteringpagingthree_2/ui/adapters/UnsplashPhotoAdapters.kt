package com.example.masteringpagingthree_2.ui.adapters // ktlint-disable package-name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.masteringpagingthree_2.data.model.UnsplashModel
import com.example.masteringpagingthree_2.databinding.UnsplashPhotoItemBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashPhotoAdapters @Inject constructor() :
    PagingDataAdapter<UnsplashModel, UnsplashPhotoAdapters.PhotoViewHolder>(DiffUtilCallBack()) {
    class PhotoViewHolder(private val binding: UnsplashPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(unsplashPhoto: UnsplashModel) {
            binding.apply {
                data = unsplashPhoto
                executePendingBindings()
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<UnsplashModel>() {
        override fun areItemsTheSame(oldItem: UnsplashModel, newItem: UnsplashModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UnsplashModel, newItem: UnsplashModel): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = UnsplashPhotoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
}
