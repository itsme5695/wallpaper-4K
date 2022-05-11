package com.example.newhdwallpaper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newhdwallpaper.R
import com.example.newhdwallpaper.databinding.Item2Binding
import com.example.newhdwallpaper.models.Hit

import com.squareup.picasso.Picasso

class RvAdapter(var itemClick: setOnClick) :
    PagingDataAdapter<Hit, RvAdapter.Vh>(MyDiffUtil()) {

    inner class Vh(var item: Item2Binding) :
        RecyclerView.ViewHolder(item.root) {
        fun onBind(photo: Hit, position: Int) {
            Picasso.get().load(photo.previewURL).placeholder(R.drawable.images).into(item.itemImage)
            photo.id
            item.root.setOnClickListener {
                itemClick.itemClicked(photo, position)
            }
        }
    }

    class MyDiffUtil() : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        getItem(position)?.let { holder.onBind(it, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(Item2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    interface setOnClick {
        fun itemClicked(photo: Hit, position: Int)
    }
}