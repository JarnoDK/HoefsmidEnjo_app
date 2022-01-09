package com.enjo.hoefsmidenjo.api.classes.invoice

import androidx.room.ColumnInfo
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem

data class ApiInvoiceLine(

    @ColumnInfo(name = "invoielineid")
    var id:Int,
    var amount:Int,
    var item:ApiInvoiceItem
)
