package com.enjo.hoefsmidenjo.database.invoice

import androidx.room.PrimaryKey
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem

data class DbInvoiceLine(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var amount:Int,
    var item:ApiInvoiceItem,
    var invoiceId:Int
)
