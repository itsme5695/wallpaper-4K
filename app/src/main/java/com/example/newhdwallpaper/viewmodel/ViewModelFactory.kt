package com.example.newhdwallpaper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newhdwallpaper.retrofit.ApiService
import java.lang.IllegalArgumentException

class ViewModelFactory(var apiService: ApiService, var category: String) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            return PhotoViewModel(apiService, category) as T
        }
        throw IllegalArgumentException("Error")
    }
}