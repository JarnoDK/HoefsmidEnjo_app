package com.enjo.hoefsmidenjo.screens.invoices.create

import android.app.Application
import android.content.Context
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.updatePadding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoice
import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoiceLine
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.asApiModel
import com.enjo.hoefsmidenjo.api.classes.services.EventApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceItemApi
import com.enjo.hoefsmidenjo.api.classes.services.UserApi
import com.enjo.hoefsmidenjo.api.classes.user.asApiUser
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.database.relations.RelInvoiceLineInvoiceItem
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

class InvoiceCreateViewModel(app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var users: Array<String> = database.userDao.getAllClientArray().map { s -> "${s.firstName} ${s.lastName}" }.sorted().toTypedArray()
    var items: Array<String> = database.invoiceItemDao.GetAllArray().map { s -> "${s.name}"}.sorted().toTypedArray()

    lateinit var selectedUser:DbUser

    var current = LocalDate.now()
    var date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    var time:String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

    var invoiceRepo:InvoiceRepository= InvoiceRepository(database)

    lateinit var invoice:DbInvoice
    var lines:MutableList<ApiInvoiceLine> = mutableListOf()


    fun addItem(name:String , amount:Int, context:Context ):TableRow{

        val row = TableRow(context)
        val itemName = TextView(context)
        val am = TextView(context)
        val total = TextView(context)
        val delete = TextView(context)

        itemName.setTextAppearance(R.style.tablerowtext)
        am.setTextAppearance(R.style.tablerowtext)
        total.setTextAppearance(R.style.tablerowprice)

        itemName.gravity = Gravity.CENTER_HORIZONTAL
        am.gravity = Gravity.CENTER_HORIZONTAL
        total.gravity = Gravity.CENTER


        val item:DbInvoiceItem = database.invoiceItemDao.getByName(name)

        var layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            )

        var unitprice:Double = item.unitPrice?:0.00
        var result:Double = unitprice*amount

        itemName.text = name
        am.text = "$amount"
        total.text = "%.2f €".format(result)



        row.addView(itemName, layoutParams)
        row.addView(am, layoutParams)
        row.addView(total,layoutParams)
        row.addView(delete,layoutParams)

        lines.add(
            ApiInvoiceLine(
                amount = amount,
                id = -1,
                item = item.asApiModel()
            )
        )


        //row.addView("%.2f".format(amount.times(item.unitPrice)), layoutParams)

        //table!!.addView(newRow)
        return row
    }

    fun addInvoice(client:String){

        var dt = current
        var timestring = time.split(":")
        var tm = LocalTime.of(timestring[0].toInt(),timestring[1].toInt())


        var datetime :LocalDateTime = LocalDateTime.of(dt.year,dt.monthValue,dt.dayOfMonth,tm.hour,tm.minute)
        var dtstring = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        Timber.tag("Datetime = ").i(dtstring)
        Timber.tag("Lines = ").i("${lines.stream().map { s -> "${s.item.name} x ${s.amount} " }.collect(
            Collectors.joining("\n"))}")

        var inv  = ApiInvoice(
            client = database.userDao.getUserByName(client).asApiUser(),
            id = -1,
            time = dtstring,
            invoiceLines = lines
        )

        coroutineScope.launch {
            invoiceRepo.addInvoice(inv)

        }
    }


    override fun onCleared() {
        super.onCleared()
        Timber.tag("LoginViewModel").i("LoginViewModel destroyed")
    }
}