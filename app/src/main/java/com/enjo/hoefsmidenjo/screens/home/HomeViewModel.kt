package com.enjo.hoefsmidenjo.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.repository.EventRepository
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel( app: Application): AndroidViewModel(app){


    var date:LocalDate= LocalDate.now()
    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val dao = database.eventDao
    var events:LiveData<List<RelUserEvent>> =  dao.getAllEventsOfDateLive(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
    val handler = CoroutineExceptionHandler() { _, exception ->
        throw AssertionError()
    }

    /**
     * Refresh events of selected date on date changed
     */
    fun refreshList(){


        val dt:String = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        events = dao.getAllEventsOfDateLive(dt)

    }

    /**
     * Read data from api and add to room database
     */
     fun reloadEvents() {
        coroutineScope.launch (handler){
                val eventRepo = EventRepository(database)
                eventRepo.insertFromApi()
        }
    }


}