package com.example.newhdwallpaper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.newhdwallpaper.paging.PhotoSource
import com.example.newhdwallpaper.retrofit.ApiService

    class PhotoViewModel(var apiService :ApiService, var category1: String) : ViewModel() {
    var photos = Pager(PagingConfig(100)) { PhotoSource(apiService, category1)
        }.flow.cachedIn(viewModelScope)
}