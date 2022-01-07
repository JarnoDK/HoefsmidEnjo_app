package com.enjo.hoefsmidenjo.repository


import com.enjo.hoefsmidenjo.api.classes.services.UserApi
import com.enjo.hoefsmidenjo.api.classes.user.asDatabaseModel
import com.enjo.hoefsmidenjo.database.RoomDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepository (private val database: RoomDb){

    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            val users = UserApi.retrofitService.getUserAsync().await()
            database.userDao.insertAll(*users.asDatabaseModel())
            Timber.i("end suspend")
        }
    }
}