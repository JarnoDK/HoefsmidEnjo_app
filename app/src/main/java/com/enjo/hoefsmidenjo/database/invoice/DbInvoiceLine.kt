package com.enjo.hoefsmidenjo.database.invoice

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem

@Entity(tableName = "invoice_line")
data class DbInvoiceLine(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var amount:Int,
    var item:Int,
    var invoiceId:Int
)
