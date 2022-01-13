package com.enjo.hoefsmidenjo.screens.appointment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppointmentCreateModelFactory(private var app:Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AppointmentCreateViewModel::class.java)){
            return AppointmentCreateViewModel(app) as T
        }
        throw IllegalArgumentException("LoginViewModel class not found")
    }
}