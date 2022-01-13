package com.enjo.hoefsmidenjo.screens.items.get

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class ItemGetViewModel( app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var itemname = ""

    private val dao = database.invoiceItemDao
    var items:LiveData<List<DbInvoiceItem>> = dao.GetAll()

    /**
     * Herlaad items met filter
     */
    fun refreshItems(){
        items = dao.GetFilteredItem(itemname)
    }



    override fun onCleared() {
        super.onCleared()

        Timber.tag("ClientViewModel").i("ClientViewModel destroyed")
    }
}