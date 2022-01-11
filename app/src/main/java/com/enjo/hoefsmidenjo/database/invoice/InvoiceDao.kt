package com.enjo.hoefsmidenjo.database.invoice

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.database.relations.RelInvoiceLineInvoiceItem

@Dao
interface InvoiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg invoice:DbInvoice )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(invoice:DbInvoice )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLine(invoiceLine: DbInvoiceLine )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInvoiceLines(invoiceLines:Array<DbInvoiceLine> )


    @Query("select * from invoice")
    fun getAll():LiveData<List<DbInvoice>>

    @Query("Select *,  (Select sum(ii.unitPrice*il.amount) from invoice_line il join invoice_item ii on il.item == ii.itemId where il.invoiceId == inv.id ) as  \"amount\" from invoice inv join users us on inv.client == us.userid order by Time")
    fun GetInvoicesWithTotalPrice():LiveData<List<RelClientInvoiceAmount>>

    @Query("SELECT * FROM invoice_line il JOIN invoice_item ii ON il.item == ii.itemId where il.invoiceId == :id Order by ii.name")
    fun getInvoiceLinesOfInvoice(id:Int):LiveData<List<RelInvoiceLineInvoiceItem>>

    @Query("Select sum(ii.unitPrice*il.amount) as amount from invoice_line il join invoice_item ii on il.item == ii.itemId where il.invoiceId == :id")
    fun getTotalAmount(id:Int):Double

    @Query("Select *,  (Select sum(ii.unitPrice*il.amount) from invoice_line il join invoice_item ii on il.item == ii.itemId where il.invoiceId == :id ) as  \"amount\" from invoice inv join users us on inv.client == us.userid where inv.id == :id Order by firstName,lastName")
    fun getById(id:Int): RelClientInvoiceAmount

    @Query("Select *,  (Select sum(ii.unitPrice*il.amount) from invoice_line il join invoice_item ii on il.item == ii.itemId where il.invoiceId == inv.id ) as  \"amount\" from invoice inv join users us on inv.client == us.userid where firstName like '%'||:firstname||'%' and lastName like '%'||:lastname||'%' and time LIKE :date||'%' Order by time")
    fun GetInvoicesWithTotalPriceFiltered(date:String,firstname:String,lastname:String):LiveData<List<RelClientInvoiceAmount>>
}