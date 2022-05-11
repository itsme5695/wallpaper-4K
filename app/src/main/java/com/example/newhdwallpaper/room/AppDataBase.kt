package com.example.newhdwallpaper.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newhdwallpaper.room.dao.WallpaperDao
import com.example.newhdwallpaper.room.entity.WallpaperEntity


@Database(entities = [WallpaperEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao(): WallpaperDao

    companion object {
        private var instance: AppDataBase? = null


        fun getInstance(context: Context): AppDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "AppDataBase"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}