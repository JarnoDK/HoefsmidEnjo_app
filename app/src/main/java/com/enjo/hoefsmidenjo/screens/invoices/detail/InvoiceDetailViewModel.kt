package com.enjo.hoefsmidenjo.screens.invoices.detail

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
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.database.relations.RelInvoiceLineInvoiceItem
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

class InvoiceDetailViewModel(app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val dao = database.invoiceDao

    lateinit var invoice: RelClientInvoiceAmount

    lateinit var invoiceItems:LiveData<List<RelInvoiceLineInvoiceItem>>
    var total:Double = 0.00

    init {
        Timber.tag("LoginViewModel").i("LoginViewModel created")
        getInvoices()
    }


    fun setInvoice(id:Int){
        invoice = dao.getById(id)
        invoiceItems = dao.getInvoiceLinesOfInvoice(id)
        total = dao.getTotalAmount(id)
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