package com.enjo.hoefsmidenjo.screens.clients.get

import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime

class ClientGetViewModel:ViewModel() {


    var date:LocalDate= LocalDate.now()


    init {
        Timber.tag("LoginViewModel").i("LoginViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag("LoginViewModel").i("LoginViewModel destroyed")
    }
}