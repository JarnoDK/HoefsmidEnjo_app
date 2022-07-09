package com.enjo.hoefsmidenjo.screens.login

import android.app.Application
import android.content.Context
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import com.enjo.hoefsmidenjo.repository.UserRepository
import kotlinx.coroutines.*
import okhttp3.internal.wait

class LoginViewModel( app: Application): AndroidViewModel(app){

    private var viewModelJob = Job()

    private val database = RoomDb.getInstance(app.applicationContext)

    private val userRepo= UserRepository(database);

    fun logIn(username: String, password: String):Boolean {
        var check = false

    runBlocking {
        check = userRepo.logIn(username, password)
    }
        return check

    }


}