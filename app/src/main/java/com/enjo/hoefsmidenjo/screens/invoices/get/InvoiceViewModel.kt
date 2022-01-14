package com.enjo.hoefsmidenjo.screens.invoices.get

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import com.enjo.hoefsmidenjo.repository.InvoiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

class InvoiceViewModel(app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val dao = database.invoiceDao
    var invoices:LiveData<List<RelClientInvoiceAmount>> = dao.GetInvoicesWithTotalPrice()

    var date = ""
    var current: LocalDate = LocalDate.now()
    var firstname = ""
    var lastname = ""

    /**
     * Haalt gegevens uit api
     */
    fun reloadInvoicesFromApi(){
        coroutineScope.launch {
            var invRepo = InvoiceRepository(database)
            invRepo.InsertFromApi()
        }

    }

    /**
     * Invullen van invoices met eventuele filter
     */
    fun refreshList(){

        invoices = dao.GetInvoicesWithTotalPriceFiltered(date,firstname,lastname)

    }


    override fun onCleared() {
        super.onCleared()
    }
}