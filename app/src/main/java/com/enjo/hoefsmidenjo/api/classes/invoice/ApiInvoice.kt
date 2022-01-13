package com.enjo.hoefsmidenjo.api.classes.invoice

import androidx.room.ColumnInfo
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController

/**
* Rekening over api
*/
data class ApiInvoice(

    @ColumnInfo(name = "invoiceid")
    var id:Int,
    var client: ApiUser,
    var time:String,
    var invoiceLines:List<ApiInvoiceLine>

){

    override fun toString(): String {
        return "id: $id client:$client time:$time, lines:${invoiceLines.size}"
    }
}

/**
* Converteer lijst van Api rekeningen naar array van database rekeningen
*/
fun List<ApiInvoice>.asDatabaseModel(): Array<DbInvoice>{
    return map{
        DbInvoice(
            id =  it.id,
            time = DomainController.instance.getTimeOfString(it.time),
            client = it.client.id
            )
    }.toTypedArray()
}
/**
* Converteer enkele api rekening naar database rekening
*/
fun ApiInvoice.asDatabaseModel(): DbInvoice{
        return DbInvoice(
            id =  id,
            time = DomainController.instance.getTimeOfString(time),
            client = client.id
        )
}

