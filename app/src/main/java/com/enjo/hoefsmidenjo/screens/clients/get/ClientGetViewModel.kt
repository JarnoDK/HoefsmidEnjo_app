package com.enjo.hoefsmidenjo.screens.clients.get

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClientGetViewModel( app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    var firstnamefilter=""
    var lastnamefilter=""


    private val userRepo = UserRepository(database)



    private val dao = database.userDao
    lateinit var users:LiveData<List<DbUser>>

    /**
     * Lijst hervullen met gegeven filters
     */
    fun refreshUserList(){

        users = dao.getAllFilteredClients(firstnamefilter,lastnamefilter)
    }

    fun reloadUsersFromApi(){
        coroutineScope.launch {
            userRepo.insertFromApi()

        }
    }

    /**
     * Gebruiker verwijderen
     * @param id van gebruiker om te verwijderen
     */
    fun removeUser(id:Int):Boolean{

        var check = true
        viewModelScope.launch {

            check = userRepo.removeUser(id)
        }
        return check
    }


}