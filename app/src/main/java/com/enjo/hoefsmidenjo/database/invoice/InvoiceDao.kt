package com.enjo.hoefsmidenjo.database.invoice

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.database.relations.RelInvoiceLineInvoiceItem

/**
 * Queries betrekkende rekeningen en rekening lijnen
 */
@Dao
interface InvoiceDao {

    /**
     * Toevoegen van rekeningen
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg invoice:DbInvoice )

    /**
     * toevoegen van een enkele rekening
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(invoice:DbInvoice )

    /**
     * toevoegen rekening lijn
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLine(invoiceLine: DbInvoiceLine )

    /**
     * Toevoegen invoicelines uit array
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInvoiceLines(invoiceLines:Array<DbInvoiceLine> )

    /**
     * alle rekeningen opvragen
     * @return Livedate database rekening
     */
    @Query("select * from invoice")
    fun getAll():LiveData<List<DbInvoice>>

    /**
     * Verkrijgen van alle rekeningen met hun client en totale prijs
     * @return Livedate met lijst van rekeningen met client en totale prijs
     */
    @Query("Select *,  (Select sum(ii.unitPrice*il.amount) from invoice_line il join invoice_item ii on il.item == ii.itemId where il.invoiceId == inv.id ) as  \"amount\" from invoice inv join users us on inv.client == us.userid order by Time")
    fun GetInvoicesWithTotalPrice():LiveData<List<RelClientInvoiceAmount>>

    /**
     * Krijg invoice lijnen van gegeven rekening inclusief hun rekening item
     * @param id rekening id
     * @return livedata lijst rekening lijnen en hun item
     */
    @Query("SELECT * FROM invoice_line il JOIN invoice_item ii ON il.item == ii.itemId where il.invoiceId == :id Order by ii.name")
    fun getInvoiceLinesOfInvoice(id:Int):LiveData<List<RelInvoiceLineInvoiceItem>>

    /**
     * totale prijs van een rekening
     * @param id rekening id
     * @return totale prijs
     */
    @Query("Select sum(ii.unitPrice*il.amount) as amount from invoice_line il join invoice_item ii on il.item == ii.itemId where il.invoiceId == :id")
    fun getTotalAmount(id:Int):Double

    /**
     * Opvragen van rekening met client en totale prijs
     * @param id rekeningid
     * @return rekening met client en totale prijs
     */
    @Query("Select *,  (Select sum(ii.unitPrice*il.amount) from invoice_line il join invoice_item ii on il.item == ii.itemId where il.invoiceId == :id ) as  \"amount\" from invoice inv join users us on inv.client == us.userid where inv.id == :id Order by firstName,lastName")
    fun getById(id:Int): RelClientInvoiceAmount

    /**
     * filter rekeningen op datum, voornaam en achternaam
     * @param date datum van rekening
     * @param firstname voornaam van klant
     * @param lastname achternaam van klant
     * @return livedate lijst van gefilterde rekening inclusief klant en totale prijs
     */
    @Query("Select *,  (Select sum(ii.unitPrice*il.amount) from invoice_line il join invoice_item ii on il.item == ii.itemId where il.invoiceId == inv.id ) as  \"amount\" from invoice inv join users us on inv.client == us.userid where firstName like '%'||:firstname||'%' and lastName like '%'||:lastname||'%' and time LIKE :date||'%' Order by time")
    fun GetInvoicesWithTotalPriceFiltered(date:String,firstname:String,lastname:String):LiveData<List<RelClientInvoiceAmount>>
}