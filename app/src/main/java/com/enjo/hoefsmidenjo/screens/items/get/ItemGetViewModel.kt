package com.enjo.hoefsmidenjo.screens.items.get

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.database.user.UserDao
import com.enjo.hoefsmidenjo.repository.InvoiceItemRepository
import com.enjo.hoefsmidenjo.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime

class ItemGetViewModel( app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var itemname = ""

    private val dao = database.invoiceItemDao
    var items:LiveData<List<DbInvoiceItem>> = dao.GetAll()

    fun refreshItems(){

        Timber.tag("Filtered").i("ItemName: ${itemname}")
        items = dao.GetFilteredItem(itemname)
    }



    override fun onCleared() {
        super.onCleared()

        Timber.tag("ClientViewModel").i("ClientViewModel destroyed")
    }
}