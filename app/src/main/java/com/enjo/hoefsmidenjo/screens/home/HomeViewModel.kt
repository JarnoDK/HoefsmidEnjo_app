package com.enjo.hoefsmidenjo.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel( app: Application): AndroidViewModel(app){


    var date:LocalDate= LocalDate.now()
    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val dao = database.eventDao
    lateinit var events:LiveData<List<RelUserEvent>>


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
     fun reloadInvoices() {
        coroutineScope.launch {
                val eventRepo = EventRepository(database)
                eventRepo.insertFromApi()
        }
    }


}