package com.enjo.hoefsmidenjo.repository

import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.asDatabaseModel
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceItemApi
import com.enjo.hoefsmidenjo.database.RoomDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class InvoiceItemRepository (private var database: RoomDb){


    /**
     * Ophalen van data uit de api en toevoegen aan room database
     */
    suspend fun insertFromApi(){

        withContext(Dispatchers.IO){
            try{
                val invoiceItems = InvoiceItemApi.retrofitService.getInvoiceItemAsync().await()
                database.invoiceItemDao.insertAll(*invoiceItems.asDatabaseModel())
            }catch (ex:HttpException){

            }

        }
    }

    /**
     * Toevoegen van data aan api en room database
     */
    suspend fun addItem(item:ApiInvoiceItem):Boolean{

        var check:Boolean
        try{
            withContext(Dispatchers.IO){
                val invoiceItem= InvoiceItemApi.retrofitService.createInvoiceItemAsync(item).await()
                database.invoiceItemDao.insert(invoiceItem.asDatabaseModel())
                check = true
            }
        }catch (ex:HttpException){
            check = false
        }
        return check
    }
}