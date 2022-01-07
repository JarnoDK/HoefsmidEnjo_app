package com.enjo.hoefsmidenjo.repository

import com.enjo.hoefsmidenjo.api.classes.event.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.database.RoomDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class EventRepository (private val database: RoomDb){

    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            // get events from api's getAll function
            val events = EventApi.retrofitService.getEventAsync().await()
            database.eventDao.insertAll(*events.asDatabaseModel())

            Timber.i("end suspend")
        }
    }
}