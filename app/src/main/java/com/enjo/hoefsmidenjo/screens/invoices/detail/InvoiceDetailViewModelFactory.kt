package com.enjo.hoefsmidenjo.screens.invoices.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InvoiceDetailViewModelFactory(private val application: Application): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InvoiceDetailViewModel::class.java)){
            return InvoiceDetailViewModel(application) as T
        }
        throw IllegalArgumentException("InvoiceDetailViewModel class not found")
    }
}