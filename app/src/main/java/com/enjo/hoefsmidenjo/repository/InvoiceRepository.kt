package com.enjo.hoefsmidenjo.repository

import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoice
import com.enjo.hoefsmidenjo.api.classes.invoice.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class InvoiceRepository (private val database: RoomDb){
    private var dao = database.invoiceDao

    /**
     * Ophalen van data uit api en toevoegen aan room database
     */
    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            val invoices = InvoiceApi.retrofitService.getInvoiceAsync().await()
            dao.insertAll(*invoices.asDatabaseModel())
            for(inv in invoices){
                dao.insertAllInvoiceLines(inv.invoiceLines.asDatabaseModel(inv.id))
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


        dao.insertAllInvoiceLines(invoice.invoiceLines.asDatabaseModel(invoice.id))
        dao.insert(invoice.asDatabaseModel())
    }


}