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

    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            val invoiceItems = InvoiceItemApi.retrofitService.getInvoiceItemAsync().await()
            //'*': kotlin spread operator.
            //Used for functions that expect a vararg param
            //just spreads the array into separate fields


            dao.insertAll(*invoiceItems.asDatabaseModel())

            Timber.i("end suspend")
        }
    }

    suspend fun addItem(item:ApiInvoiceItem){
        withContext(Dispatchers.IO){
                val invoiceItem= InvoiceItemApi.retrofitService.createInvoiceItemAsync(item).await()
                dao.insert(invoiceItem.asDatabaseModel())
        }
    }
}