package com.enjo.hoefsmidenjo.database.invoiceitem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoice_item")
data class DbInvoiceItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="itemId")
    var id:Int,
    var name: String?=null,
    var unitPrice:Double? = 0.00
)