package com.enjo.hoefsmidenjo.database.invoiceitem

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice

@Dao
interface InvoiceItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: DbInvoiceItem)

    @Query("SELECT * FROM invoice_item")
    fun GetAll():LiveData<List<DbInvoiceItem>>

    @Query("SELECT * FROM invoice_item WHERE name LIKE '%'||:filter||'%'")
    fun GetFilteredItem(filter:String):LiveData<List<DbInvoiceItem>>

}