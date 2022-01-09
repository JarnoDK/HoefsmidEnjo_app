package com.enjo.hoefsmidenjo.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceItemApi
import com.enjo.hoefsmidenjo.api.classes.services.UserApi
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.repository.EventRepository
import com.enjo.hoefsmidenjo.repository.InvoiceItemRepository
import com.enjo.hoefsmidenjo.repository.InvoiceRepository
import com.enjo.hoefsmidenjo.repository.UserRepository
import kotlinx.coroutines.*
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
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
        getInvoices()
    }

    fun refreshList(){


        var dt:String = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        events = dao.getAllEventsOfDateLive(dt)

    }

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

                Timber.tag("Error").i("Could not load invoices")

                throw t
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag("LoginViewModel").i("LoginViewModel destroyed")
    }
}