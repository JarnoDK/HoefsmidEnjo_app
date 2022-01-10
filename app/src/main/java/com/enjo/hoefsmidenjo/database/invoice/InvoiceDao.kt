package com.enjo.hoefsmidenjo.database.invoice

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount

@Dao
interface InvoiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg invoice:DbInvoice )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLine(invoiceLine: DbInvoiceLine )

    @Query("select * from invoice")
    fun getAll():LiveData<List<DbInvoice>>

    @Query("Select *,  (Select sum(ii.unitPrice*il.amount) from invoice_line il join invoice_item ii on il.item == ii.id where il.invoiceId == inv.id ) as  \"amount\" from invoice inv join users us on inv.client == us.userid")
    fun GetInvoicesWithTotalPrice():LiveData<List<RelClientInvoiceAmount>>
}