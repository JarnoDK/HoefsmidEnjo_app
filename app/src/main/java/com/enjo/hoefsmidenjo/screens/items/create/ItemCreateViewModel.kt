package com.enjo.hoefsmidenjo.screens.items.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import com.enjo.hoefsmidenjo.repository.InvoiceItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

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
    var items:LiveData<List<DbInvoiceItem>> = database.invoiceItemDao.getAll()

    /**
     * Toevoegen van rekening met controle
     */
    fun createInvoiceItem():Boolean {
        errors = ""
        val item = ApiInvoiceItem(
            id = -1,
            name = name,
            unitPrice = price
        )

        // naam leeg
        if (name == null || name.trim() == "") {
            errors += "Item naam kan niet leeg zijn\n"
            check = false
        }

        // prijs kleiner of gelijk aan 0
        if (price <= 0) {
            errors += "Prijs moet groter zijn dan 0"
            check = false
        }

        // item bestaat al
            if (dao.itemExist(name)) {
                errors += "Item bestaat reeds"
                check=false
            }

        coroutineScope.launch {

            if (check) {
                if(itemRepo.addItem(item)){
                    errors=""
                    false
                }else{
                    errors+="Kan geen verbinding maken met de databank"
                    check = false
                }
            }
        }
        return check
    }


}