package com.enjo.hoefsmidenjo.screens.home

import androidx.lifecycle.ViewModel
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceItemApi
import com.enjo.hoefsmidenjo.api.classes.services.UserApi
import com.enjo.hoefsmidenjo.api.repository.InvoiceRepository
import kotlinx.coroutines.*
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime

class HomeViewModel:ViewModel() {


    var date:LocalDate= LocalDate.now()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        Timber.tag("LoginViewModel").i("LoginViewModel created")
        getInvoices()
    }

    private fun getInvoices() {
        // Call API
        coroutineScope.launch {
            try {
                val invoices = InvoiceApi.retrofitService.getInvoiceAsync().await()
                Timber.tag("Invoices").i(invoices.size.toString())

                val invoiceitems = InvoiceItemApi.retrofitService.getInvoiceItemAsync().await()
                Timber.tag("Invoiceitems").i(invoiceitems.size.toString())

                val clients = UserApi.retrofitService.getUserAsync().await()
                Timber.tag("clients").i(clients.size.toString())

                val events = EventApi.retrofitService.getEventAsync().await()
                Timber.tag("Events").i(events.size.toString())

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