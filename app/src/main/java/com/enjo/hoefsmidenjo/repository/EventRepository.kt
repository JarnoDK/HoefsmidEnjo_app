package com.enjo.hoefsmidenjo.repository

import com.enjo.hoefsmidenjo.api.classes.event.ApiEvent
import com.enjo.hoefsmidenjo.api.classes.event.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceItemApi
import com.enjo.hoefsmidenjo.database.RoomDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class EventRepository (private val database: RoomDb){

    /**
     * Ophalen data van api en toevoegen aan room database
     */
    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            // get events from api's getAll function
            val events = EventApi.retrofitService.getEventAsync().await()
            database.eventDao.insertAll(*events.asDatabaseModel())

            Timber.i("end suspend")
        }
    }

    /**
     * Verwijderen van data uit zowel api als room database
     */
    suspend fun addEvent(ev:ApiEvent){
        withContext(Dispatchers.IO){

            Timber.tag("received event").i(ev.toString())
            val event = EventApi.retrofitService.createEventAsync(ev).await()
            database.eventDao.insert(event.asDatabaseModel())
        }
    }


}