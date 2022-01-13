package com.enjo.hoefsmidenjo.screens.invoices.get

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InvoiceViewModelFactory(private val application: Application): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InvoiceViewModel::class.java)){
            return InvoiceViewModel(application) as T
        }
        throw IllegalArgumentException("InvoiceViewModel class not found")
    }
}