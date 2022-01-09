package com.enjo.hoefsmidenjo.database.invoice

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InvoiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg invoice:DbInvoice )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLine(invoiceLine: DbInvoiceLine )

    @Query("select * from invoice")
    fun getAll():LiveData<List<DbInvoice>>
}