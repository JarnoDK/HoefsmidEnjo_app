package com.enjo.hoefsmidenjo.screens.items.create

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ItemCreateViewModelFactory(private val application: Application): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemCreateViewModel::class.java)){
            return ItemCreateViewModel(application) as T
        }
        throw IllegalArgumentException("ItemCreateViewModel class not found")
    }
}