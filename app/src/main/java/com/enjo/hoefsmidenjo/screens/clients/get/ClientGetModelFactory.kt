package com.enjo.hoefsmidenjo.screens.clients.get

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ClientGetModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClientGetViewModel::class.java)){
            return ClientGetViewModel() as T
        }
        throw IllegalArgumentException("LoginViewModel class not found")
    }
}