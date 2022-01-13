package com.enjo.hoefsmidenjo.screens.clients.get

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClientGetModelFactory(private var app:Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClientGetViewModel::class.java)){
            return ClientGetViewModel(app) as T
        }
        throw IllegalArgumentException("ClientGetViewModel class not found")
    }
}