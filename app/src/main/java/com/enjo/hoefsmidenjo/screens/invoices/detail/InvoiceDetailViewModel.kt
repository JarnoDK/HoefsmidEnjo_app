package com.enjo.hoefsmidenjo.screens.invoices.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.database.relations.RelInvoiceLineInvoiceItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

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
    }


    fun setInvoice(id:Int){
        invoice = dao.getById(id)
        invoiceItems = dao.getInvoiceLinesOfInvoice(id)
        total = dao.getTotalAmount(id)
    }



    override fun onCleared() {
        super.onCleared()
        Timber.tag("LoginViewModel").i("LoginViewModel destroyed")
    }
}