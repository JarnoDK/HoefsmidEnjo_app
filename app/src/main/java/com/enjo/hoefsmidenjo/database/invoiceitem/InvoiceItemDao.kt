package com.enjo.hoefsmidenjo.database.invoiceitem

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InvoiceItemDao {

    /**
     * Toevoegen van Database rekening lijnen
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: DbInvoiceItem)

    /**
     * Leeg de invoice item database
     */
    @Query("DELETE FROM invoice_item")
    fun clearItems()
    /**
     * Ophalen van alle rekening items
     * @return Livedate lijst met database rekening items
     */
    @Query("SELECT * FROM invoice_item")
    fun getAll():LiveData<List<DbInvoiceItem>>


    /**
     * Ophalen van alle rekening items
     * @return Array van database rekening items
     */
    @Query("SELECT * FROM invoice_item")
    fun getAllArray():Array<DbInvoiceItem>

    /**
     * Ophalen van gefilterde rekeningen
     * @param filter Naam van item
     * @return Livedata lijst database rekening items
     */
    @Query("SELECT * FROM invoice_item WHERE name LIKE '%'||:filter||'%'")
    fun getFilteredItem(filter:String):LiveData<List<DbInvoiceItem>>

    /**
     * Toevoegen van database rekening item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DbInvoiceItem)

    /**
     * Get invoice item by id
     * @param id item id
     * @return invoiceitem with id
     */
    @Query("SELECT * FROM invoice_item WHERE itemId == :id")
    fun getById(id:Int):DbInvoiceItem
    /**
     * Controleert of item naam reeds bestaat
     * @return true indien item bestaat
     */
    @Query("SELECT EXISTS(SELECT * FROM invoice_item where name == :name)")
    fun itemExist(name:String): Boolean

    /**
     * Ophalen item waar naam gelijk is aan gegeven naam
     * @param name van database rekening item
     * @return database rekening item
     */
    @Query("SELECT * FROM invoice_item where name == :name")
    fun getByName(name:String):DbInvoiceItem


}