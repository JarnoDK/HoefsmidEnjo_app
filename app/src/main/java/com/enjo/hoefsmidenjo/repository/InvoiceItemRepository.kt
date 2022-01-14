package com.enjo.hoefsmidenjo.repository

import com.enjo.hoefsmidenjo.api.classes.event.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.invoice.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceItemApi
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class InvoiceItemRepository (private val database: RoomDb){

    var dao = database.invoiceItemDao

    /**
     * Ophalen van data uit de api en toevoegen aan room database
     */
    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            dao.clearItems()
            val invoiceItems = InvoiceItemApi.retrofitService.getInvoiceItemAsync().await()
            dao.insertAll(*invoiceItems.asDatabaseModel())
        }
    }

    /**
     * Toevoegen van data aan api en room database
     */
    suspend fun addItem(item:ApiInvoiceItem){
        withContext(Dispatchers.IO){
                val invoiceItem= InvoiceItemApi.retrofitService.createInvoiceItemAsync(item).await()
                dao.insert(invoiceItem.asDatabaseModel())
        }
    }
}