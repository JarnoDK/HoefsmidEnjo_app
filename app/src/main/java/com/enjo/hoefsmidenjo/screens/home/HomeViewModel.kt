package com.enjo.hoefsmidenjo.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.repository.EventRepository
import com.enjo.hoefsmidenjo.repository.InvoiceItemRepository
import com.enjo.hoefsmidenjo.repository.InvoiceRepository
import com.enjo.hoefsmidenjo.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel( app: Application): AndroidViewModel(app){


    var date:LocalDate= LocalDate.now()
    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val dao = database.eventDao
    lateinit var events:LiveData<List<RelUserEvent>>

    init {
        Timber.tag("LoginViewModel").i("LoginViewModel created")

        // read API
        getInvoices()
    }

    /**
     * Refresh events of selected date on date changed
     */
    fun refreshList(){


        var dt:String = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        events = dao.getAllEventsOfDateLive(dt)

    }

    /**
     * Read data from api and add to room database
     */
    private fun getInvoices() {
        // Call API
        coroutineScope.launch {
            try {

                var invRepo = InvoiceRepository(database)
                invRepo.InsertFromApi()

                var itemRepo = InvoiceItemRepository(database)
                itemRepo.InsertFromApi()
                var invoiceRepo = InvoiceItemRepository(database)
                invoiceRepo.InsertFromApi()
                var eventRepo = EventRepository(database)
                eventRepo.InsertFromApi()
                var userRepo = UserRepository(database)
                userRepo.InsertFromApi()

            } catch (t: Throwable) {

                Timber.tag("Error").i("Kan geen contact maken met repository")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}