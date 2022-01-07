package com.enjo.hoefsmidenjo.repository

import com.enjo.hoefsmidenjo.api.classes.event.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.invoice.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class InvoiceRepository (private val database: RoomDb){

    suspend fun InsertFromApi(){

        withContext(Dispatchers.IO){
            val invoices = InvoiceApi.retrofitService.getInvoiceAsync().await()
            //'*': kotlin spread operator.
            //Used for functions that expect a vararg param
            //just spreads the array into separate fields

            var dao = database.invoiceDao

            dao.insertAll(*invoices.asDatabaseModel())

            for(inv in invoices){
                for(line in inv.invoiceLines){
                    dao.insertLine(
                        DbInvoiceLine(
                            id = line.id,
                            amount = line.amount,
                            item = line.item.id,
                            invoiceId = inv.id
                        )
                    )
                }
            }
            Timber.i("end suspend")
        }
    }
}