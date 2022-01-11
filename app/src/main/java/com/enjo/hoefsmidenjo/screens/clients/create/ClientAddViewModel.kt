package com.enjo.hoefsmidenjo.screens.clients.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.database.user.UserDao
import com.enjo.hoefsmidenjo.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime

class ClientAddViewModel( app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var firstname=""
    var lastname=""
    var email=""
    var telephone=""


    private val userRepo = UserRepository(database)

    private val dao = database.userDao
    var errors:String = ""

    fun resetValues(){
        errors = ""
        firstname = ""
        lastname = ""
        email = ""
        telephone = ""
    }

    fun addUser():Boolean{

        errors = ""
        var user = ApiUser(
            firstName = firstname,
            lastName = lastname,
            email = email,
            phone = telephone,
            role = 0,
            id = -1,
            password = "",
            pincode ="1234"
            )
        var check = true
        if(isEmpty(user.firstName)){
            errors += "Voornaam kan niet leeg zijn\n"
            check = false
        }
        if(isEmpty(user.lastName)){
            errors += "Achternaam kan niet leeg zijn\n"
            check = false
        }
        if(isEmpty(user.phone.orEmpty())){
            errors += "Telefoon kan niet leeg zijn\n"
            check = false
        }
        if(isEmpty(user.email)){
            errors += "Email kan niet leeg zijn\n"
            check = false
        }

        viewModelScope.launch {
            if(check) {

                try {
                    Timber.tag("Create user").i(user.toString())
                    userRepo.addUser(user)
                }catch (t:Throwable){
                    throw t
                }

            }
        }

        return check

    }

    private fun isEmpty(text: String):Boolean{
        return (text == null || text.trim() == "")
    }
    override fun onCleared() {
        super.onCleared()

        Timber.tag("ClientViewModel").i("ClientViewModel destroyed")
    }
}