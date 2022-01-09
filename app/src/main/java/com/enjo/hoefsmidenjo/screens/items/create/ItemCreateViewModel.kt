package com.enjo.hoefsmidenjo.screens.items.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceItemApi
import com.enjo.hoefsmidenjo.api.classes.services.UserApi
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.repository.EventRepository
import com.enjo.hoefsmidenjo.repository.InvoiceItemRepository
import com.enjo.hoefsmidenjo.repository.InvoiceRepository
import com.enjo.hoefsmidenjo.repository.UserRepository
import kotlinx.coroutines.*
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ItemCreateViewModel( app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val dao = database.invoiceItemDao
    private val itemRepo= InvoiceItemRepository(database)

    var name:String = ""
    var price:Double = -1.00

    var errors:String = ""
    private var check: Boolean = true



    init {
        Timber.tag("item create viewmodel").i("LoginViewModel created")
    }
    var items:LiveData<List<DbInvoiceItem>> = database.invoiceItemDao.GetAll()

    fun createInvoiceItem():Boolean {
        errors = ""
        var item = ApiInvoiceItem(
            id = -1,
            name = name,
            unitPrice = price
        )


        if (name == null || name.trim() == "") {
            errors += "Item naam kan niet leeg zijn\n"
            check = false
        }

        if (price <= 0) {
            errors += "Prijs moet groter zijn dan 0"
            check = false
        }


            if (dao.itemExist(name)) {
                errors += "Item bestaat reeds"
                check=false
            }

        coroutineScope.launch {

            if (check) {
                itemRepo.addItem(item)
            }
        }
        return check
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag("LoginViewModel").i("LoginViewModel destroyed")
    }
}