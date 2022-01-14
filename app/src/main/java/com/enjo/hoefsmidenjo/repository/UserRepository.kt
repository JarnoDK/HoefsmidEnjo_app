package com.enjo.hoefsmidenjo.repository


import com.enjo.hoefsmidenjo.api.classes.services.UserApi
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.api.classes.user.asDatabaseModel
import com.enjo.hoefsmidenjo.database.RoomDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class UserRepository (private val database: RoomDb){

    /**
     * Ophalen van data uit api en toevoegen aan room database
     */
    suspend fun insertFromApi(){

        try {
            withContext(Dispatchers.IO){
                val users = UserApi.retrofitService.getUserAsync().await()
                database.userDao.insertAll(*users.asDatabaseModel())
            }
        }catch (ex:HttpException){ }

    }
    /**
     * Verwijderen van gebruiker uit api en room database
     */
    suspend fun removeUser(id:Int):Boolean{

        var check:Boolean
        withContext(Dispatchers.IO) {
            try {
                var bool:Boolean= UserApi.retrofitService.removeUserAsync(id).await()
                database.userDao.deleteUser(id)
                check = true
            }catch (ex:HttpException){
             check =false
            }
        }
        return check
    }

    /**
     * Toevoegen van gebruiker aan api en roomdatabase
     */
    suspend fun addUser(user:ApiUser):Boolean{
        var check:Boolean
        withContext(Dispatchers.IO) {

            try{
                val user = UserApi.retrofitService.createUserAsync(user).await()
                database.userDao.insert(user.asDatabaseModel())
                check = true
            }catch (ex:HttpException){
                check = false
            }


        }
        return check
    }


}