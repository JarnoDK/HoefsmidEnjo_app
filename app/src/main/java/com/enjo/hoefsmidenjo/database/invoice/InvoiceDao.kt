package com.enjo.hoefsmidenjo.database.invoice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.enjo.hoefsmidenjo.database.event.DbEvent

@Dao
interface InvoiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg invoice:DbInvoice )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLine(invoiceLine: DbInvoiceLine )


}