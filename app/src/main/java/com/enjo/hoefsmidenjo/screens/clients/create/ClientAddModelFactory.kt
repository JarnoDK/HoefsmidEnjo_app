package com.enjo.hoefsmidenjo.screens.clients.create

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ClientAddModelFactory(private var app:Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClientAddViewModel::class.java)){
            return ClientAddViewModel(app) as T
        }
        throw IllegalArgumentException("LoginViewModel class not found")
    }
}