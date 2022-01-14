package com.enjo.hoefsmidenjo.repository

import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoice
import com.enjo.hoefsmidenjo.api.classes.invoice.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import com.enjo.hoefsmidenjo.database.RoomDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class InvoiceRepository (private val database: RoomDb){

    /**
     * Ophalen van data uit api en toevoegen aan room database
     */
    suspend fun insertFromApi(){

        withContext(Dispatchers.IO){
            database.invoiceDao.clearInvoice()
            database.invoiceDao.clearInvoiceLine()
            val invoices = InvoiceApi.retrofitService.getInvoiceAsync().await()
            database.invoiceDao.insertAll(*invoices.asDatabaseModel())
            for(inv in invoices){
                database.invoiceDao.insertAllInvoiceLines(inv.invoiceLines.asDatabaseModel(inv.id))
            }
            Timber.i("end suspend")
        }
    }

    /**
     * Toevoegen van rekening aan api en room database
     */
    suspend fun addInvoice(inv:ApiInvoice){
        Timber.tag("Added invoice").i("$inv")
        val invoice:ApiInvoice = InvoiceApi.retrofitService.createInvoiceAsync(inv).await()


        database.invoiceDao.insertAllInvoiceLines(invoice.invoiceLines.asDatabaseModel(invoice.id))
        database.invoiceDao.insert(invoice.asDatabaseModel())
    }


}