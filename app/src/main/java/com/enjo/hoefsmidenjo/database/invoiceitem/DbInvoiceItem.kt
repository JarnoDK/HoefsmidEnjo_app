package com.enjo.hoefsmidenjo.database.invoiceitem

import androidx.room.PrimaryKey

data class DbInvoiceItem(
    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var name: String?="naamloos",
    var unitPrice:Double? = 0.00
)