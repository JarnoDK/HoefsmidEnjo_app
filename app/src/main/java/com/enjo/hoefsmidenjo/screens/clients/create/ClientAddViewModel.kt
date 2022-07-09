package com.enjo.hoefsmidenjo.screens.clients.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.repository.UserRepository

import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

class ClientAddViewModel( app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)

    var firstname=""
    var lastname=""
    var email=""
    var telephone=""


    private val userRepo = UserRepository(database)

    var errors:String = ""

    /**
     * Resetten van data naar originele staat
     */
    fun resetValues(){
        errors = ""
        firstname = ""
        lastname = ""
        email = ""
        telephone = ""
    }

    /**
     * Gebruiker toevoegen
     * Controleert op voornaam, achternaam, email en telefoon niet leeg
     * @return true indien gebruiker is aangemaakt
     */
    fun addUser():Boolean{

        errors = ""
        val user = ApiUser(
            firstName = firstname,
            lastName = lastname,
            email = email,
            phone = telephone,
            role = 0,
            id = -1,
            password = ""
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

                if(userRepo.addUser(user)){
                    errors = ""
                    check = true
                }
                else{
                        errors +="Kan geen verbinding maken met databank"
                        check = false
                    }



            }
        }

        return check

    }

    /**
     * Controleer of string leeg is
     */
    private fun isEmpty(text: String):Boolean{
        return (text == null || text.trim() == "")
    }
    override fun onCleared() {
        super.onCleared()

        Timber.tag("ClientViewModel").i("ClientViewModel destroyed")
    }
}