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

    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            val users = UserApi.retrofitService.getUserAsync().await()
            database.userDao.insertAll(*users.asDatabaseModel())
            Timber.i("end suspend")
        }
    }

    suspend fun removeUser(id:Int){
        withContext(Dispatchers.IO) {

            var bool:Boolean=  UserApi.retrofitService.removeUserAsync(id).await()
            Timber.tag("Deleted: ${bool.toString()}")
            database.userDao.deleteUser(id)
            Timber.i("end suspend")

        }
    }

    suspend fun addUser(user:ApiUser){
        withContext(Dispatchers.IO) {

            var user = UserApi.retrofitService.createUserAsync(user).await()
            database.userDao.insert(user.asDatabaseModel())
            Timber.i("end suspend")

        }
    }


}