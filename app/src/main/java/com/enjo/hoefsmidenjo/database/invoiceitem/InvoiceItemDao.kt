package com.enjo.hoefsmidenjo.database.invoiceitem

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice

interface InvoiceItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: DbInvoiceItem)

}