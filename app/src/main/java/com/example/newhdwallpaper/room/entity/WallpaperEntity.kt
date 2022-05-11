package com.example.newhdwallpaper.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "wallpaper_table")
data class WallpaperEntity(
    @PrimaryKey()
    val id: Int=-1,
    val webformatURL: String,
    var likeable:Boolean=false
):Serializable