package com.enjo.hoefsmidenjo.database.invoice

import androidx.room.PrimaryKey
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser

data class DbInvoice(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var client: ApiUser,
    var time:String,
    //var invoiceLines:List<ApiInvoiceLine>

)
