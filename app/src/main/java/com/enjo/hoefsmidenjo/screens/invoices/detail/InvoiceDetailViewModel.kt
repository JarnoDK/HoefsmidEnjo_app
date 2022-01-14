package com.enjo.hoefsmidenjo.screens.invoices.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.database.relations.RelInvoiceLineInvoiceItem


class InvoiceDetailViewModel(app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)

    private val dao = database.invoiceDao

    lateinit var invoice: RelClientInvoiceAmount

    lateinit var invoiceItems:LiveData<List<RelInvoiceLineInvoiceItem>>
    var total:Double = 0.00

    /**
     * Zetten van invoice, invoiceitem en totaal door middel van id
     * @param id Rekening ID
     */
    fun setInvoice(id:Int){
        invoice = dao.getById(id)
        invoiceItems = dao.getInvoiceLinesOfInvoice(id)
        total = dao.getTotalAmount(id)
    }


}