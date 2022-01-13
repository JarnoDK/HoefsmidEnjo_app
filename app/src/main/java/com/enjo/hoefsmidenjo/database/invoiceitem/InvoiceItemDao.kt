package com.enjo.hoefsmidenjo.database.invoiceitem

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice

@Dao
interface InvoiceItemDao {

    /**
     * Toevoegen van Database rekening lijnen
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: DbInvoiceItem)

    /**
     * Ophalen van alle rekening items
     * @return Livedate lijst met database rekening items
     */
    @Query("SELECT * FROM invoice_item")
    fun GetAll():LiveData<List<DbInvoiceItem>>


    /**
     * Ophalen van alle rekening items
     * @return Array van database rekening items
     */
    @Query("SELECT * FROM invoice_item")
    fun GetAllArray():Array<DbInvoiceItem>

    /**
     * Ophalen van gefilterde rekeningen
     * @param filter Naam van item
     * @return Livedata lijst database rekening items
     */
    @Query("SELECT * FROM invoice_item WHERE name LIKE '%'||:filter||'%'")
    fun GetFilteredItem(filter:String):LiveData<List<DbInvoiceItem>>

    /**
     * Toevoegen van database rekening item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DbInvoiceItem)

    /**
     * Controleert of item naam reeds bestaat
     * @return true indien item bestaat
     */
    @Query("SELECT EXISTS(SELECT * FROM invoice_item where name == :name)")
    fun itemExist(name:String): Boolean

    /**
     * Ophalen item waar naam gelijk is aan gegeven naam
     * @param naam van database rekening item
     * @return database rekening item
     */
    @Query("SELECT * FROM invoice_item where name == :name")
    fun getByName(name:String):DbInvoiceItem


}