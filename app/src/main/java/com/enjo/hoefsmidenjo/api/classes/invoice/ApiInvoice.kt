package com.enjo.hoefsmidenjo.api.classes.invoice

import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine

data class ApiInvoice(

    var id:Int,
    var client: ApiUser,
    var time:String,
    var invoiceLines:List<ApiInvoiceLine>

)

fun List<ApiInvoice>.asDatabaseModel(): Array<DbInvoice>{
    return map{
        DbInvoice(
            id =  it.id,
            time = it.time,
            client = it.client.id
            )
    }.toTypedArray()
}
