package com.enjo.hoefsmidenjo.database.invoice

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database model van invoice lijnen
 * naar database invoice_line
 */
@Entity(tableName = "invoice_line")
data class DbInvoiceLine(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var amount:Int,
    var item:Int,
    var invoiceId:Int
)
