package com.example.newhdwallpaper.models

import com.example.newhdwallpaper.models.Hit

data class Photo(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)