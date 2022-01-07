package com.enjo.hoefsmidenjo.database.relations

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RelInvoiceLineInvoiceItem")
class RelInvoiceLineInvoiceItem (
    @PrimaryKey(autoGenerate = true)
    var id:Int? = -1,
    var invoiceLineId:Int,
    var invoiceItemId:Int
)