package com.enjo.hoefsmidenjo.screens.items.get

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ItemGetModelFactory(private var app:Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemGetViewModel::class.java)){
            return ItemGetViewModel(app) as T
        }
        throw IllegalArgumentException("LoginViewModel class not found")
    }
}