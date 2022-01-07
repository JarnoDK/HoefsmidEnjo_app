package com.enjo.hoefsmidenjo.database.invoice

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser

@Entity(tableName = "invoice")
data class DbInvoice(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var client: Int,
    var time:String,
    //var invoiceLines:List<ApiInvoiceLine>

)
