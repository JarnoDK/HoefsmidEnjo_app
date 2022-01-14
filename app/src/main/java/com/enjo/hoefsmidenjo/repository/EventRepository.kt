package com.enjo.hoefsmidenjo.repository

import com.enjo.hoefsmidenjo.api.classes.event.ApiEvent
import com.enjo.hoefsmidenjo.api.classes.event.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.database.RoomDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.lang.RuntimeException

class EventRepository (private val database: RoomDb){

    /**
     * Ophalen data van api en toevoegen aan room database
     */
    suspend fun insertFromApi(){

        withContext(Dispatchers.IO){
            try{
                val events = EventApi.retrofitService.getEventAsync().await()
                database.eventDao.insertAll(*events.asDatabaseModel())
            }catch(e:HttpException){ }


        }
    }

    /**
     * Verwijderen van data uit api en room database
     */
    suspend fun addEvent(ev:ApiEvent):Boolean{
        var check:Boolean

        withContext(Dispatchers.IO){
            try{
                val event = EventApi.retrofitService.createEventAsync(ev).await()
                database.eventDao.insert(event.asDatabaseModel())
                check = true
            }catch(ex:HttpException){
                check = false
            }

        }
        return check;
    }


}