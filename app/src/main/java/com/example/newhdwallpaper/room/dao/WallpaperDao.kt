package com.example.newhdwallpaper.room.dao

import androidx.room.*
import com.example.newhdwallpaper.room.entity.WallpaperEntity

@Dao
interface WallpaperDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(photo: WallpaperEntity)

    @Query("select * from wallpaper_table")
    fun getAll(): List<WallpaperEntity>

    @Query("UPDATE wallpaper_table SET likeable = :likable where id = :id")
    fun edit(id: Int, likable: Boolean)

    @Query("Delete from wallpaper_table where id=:id")
    fun deleteSelected(id: Int)
}