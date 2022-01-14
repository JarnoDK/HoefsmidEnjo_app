package com.enjo.hoefsmidenjo.repository


import com.enjo.hoefsmidenjo.api.classes.services.UserApi
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.api.classes.user.asApiUser
import com.enjo.hoefsmidenjo.api.classes.user.asDatabaseModel
import com.enjo.hoefsmidenjo.database.RoomDb
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepository (private val database: RoomDb){

    /**
     * Ophalen van data uit api en toevoegen aan room database
     */
    suspend fun insertFromApi(){

        withContext(Dispatchers.IO){
            database.userDao.clearUsers()
            val users = UserApi.retrofitService.getUserAsync().await()
            database.userDao.insertAll(*users.asDatabaseModel())
        }
    }
    /**
     * Verwijderen van gebruiker uit api en room database
     */
    suspend fun removeUser(id:Int){
        withContext(Dispatchers.IO) {

            try {
                var bool:Boolean=  UserApi.retrofitService.removeUserAsync(id).await()
                database.userDao.deleteUser(id)
            }catch (t: Throwable){
                Timber.tag("Geen connectie").i("Kan geen verbinding maken met api")
            }



        }
    }

    /**
     * Toevoegen van gebruiker aan api en roomdatabase
     */
    suspend fun addUser(user:ApiUser){
        withContext(Dispatchers.IO) {

            try{
                val user = UserApi.retrofitService.createUserAsync(user).await()
                database.userDao.insert(user.asDatabaseModel())
            }catch (t: Throwable){
                throw t
            }


        }
    }


}