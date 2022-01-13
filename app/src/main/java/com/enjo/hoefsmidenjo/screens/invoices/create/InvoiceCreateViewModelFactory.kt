package com.enjo.hoefsmidenjo.screens.invoices.create

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InvoiceCreateViewModelFactory(private val application: Application): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InvoiceCreateViewModel::class.java)){
            return InvoiceCreateViewModel(application) as T
        }
        throw IllegalArgumentException("InvoiceCreateViewModel class not found")
    }
}