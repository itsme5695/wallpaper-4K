package com.example.newhdwallpaper.ui.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RandomViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is random Fragment"
    }
    val text: LiveData<String> = _text
}