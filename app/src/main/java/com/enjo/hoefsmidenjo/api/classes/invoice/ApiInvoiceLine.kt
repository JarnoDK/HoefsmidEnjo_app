package com.enjo.hoefsmidenjo.api.classes.invoice

import androidx.room.ColumnInfo
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine

data class ApiInvoiceLine(

    @ColumnInfo(name = "invoielineid")
    var id:Int,
    var amount:Int,
    var item:ApiInvoiceItem
)

fun ApiInvoiceLine.asDatabaseModel():DbInvoiceLine{
    return DbInvoiceLine(
        id = id,
        amount = amount,
        item = item.id,
        invoiceId = id
    )
}



fun List<ApiInvoiceLine>.asDatabaseModel(invoiceId:Int):Array<DbInvoiceLine>{
    return map{
        DbInvoiceLine(
            id = it.id,
            amount = it.amount,
            item = it.item.id,
            invoiceId = invoiceId
        )
    }.toTypedArray()
}
