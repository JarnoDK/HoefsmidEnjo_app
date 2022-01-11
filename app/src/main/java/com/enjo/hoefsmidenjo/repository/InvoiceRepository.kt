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

    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            val invoices = InvoiceApi.retrofitService.getInvoiceAsync().await()


            // insert invoices
            dao.insertAll(*invoices.asDatabaseModel())
            // insert invoice lines
            for(inv in invoices){
                dao.insertAllInvoiceLines(inv.invoiceLines.asDatabaseModel(inv.id))
            }
            Timber.i("end suspend")
        }
    }

    suspend fun addInvoice(inv:ApiInvoice){

        val invoice:ApiInvoice = InvoiceApi.retrofitService.createInvoiceAsync(inv).await()


        dao.insertAllInvoiceLines(invoice.invoiceLines.asDatabaseModel(invoice.id))
        dao.insert(invoice.asDatabaseModel())
    }


}