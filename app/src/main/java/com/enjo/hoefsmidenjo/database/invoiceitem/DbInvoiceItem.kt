package com.enjo.hoefsmidenjo.database.invoiceitem

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoice_item")
data class DbInvoiceItem(
    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var name: String?=null,
    var unitPrice:Double? = 0.00
)