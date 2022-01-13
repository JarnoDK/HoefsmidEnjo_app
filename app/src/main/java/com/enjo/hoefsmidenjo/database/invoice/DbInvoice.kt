package com.enjo.hoefsmidenjo.database.invoice

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoice")
data class DbInvoice(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var client: Int,
    var time:String,
    //var invoiceLines:List<ApiInvoiceLine>

)
