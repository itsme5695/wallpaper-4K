package com.example.newhdwallpaper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newhdwallpaper.R
import com.example.newhdwallpaper.databinding.Item2Binding
import com.example.newhdwallpaper.room.entity.WallpaperEntity
import com.squareup.picasso.Picasso

class LikedRvAdapter(
    var list: ArrayList<WallpaperEntity>,
    var itemCLick: LikedRvAdapter.itemOnCLick
) :
    RecyclerView.Adapter<LikedRvAdapter.Vh>() {
    inner class Vh(var item: Item2Binding) : RecyclerView.ViewHolder(item.root) {
        fun onBind(photo: WallpaperEntity, position: Int) {
            Picasso.get().load(photo.webformatURL).error(R.drawable.holder2).into(item.itemImage)
            item.root.setOnClickListener {
                itemCLick.itemClick(photo, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh = Vh(
        Item2Binding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface itemOnCLick {
        fun itemClick(photo: WallpaperEntity, position: Int)
    }
}