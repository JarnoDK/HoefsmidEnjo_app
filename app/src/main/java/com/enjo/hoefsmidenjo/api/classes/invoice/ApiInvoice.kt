package com.enjo.hoefsmidenjo.api.classes.invoice

import androidx.room.ColumnInfo
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController

data class ApiInvoice(

    @ColumnInfo(name = "invoiceid")
    var id:Int,
    var client: ApiUser,
    var time:String,
    var invoiceLines:List<ApiInvoiceLine>

)

fun List<ApiInvoice>.asDatabaseModel(): Array<DbInvoice>{
    return map{
        DbInvoice(
            id =  it.id,
            time = DomainController.instance.convertDateTime(it.time),
            client = it.client.id
            )
    }.toTypedArray()
}

