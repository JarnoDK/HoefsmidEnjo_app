package com.enjo.hoefsmidenjo.api.classes.invoiceitem

import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem

data class ApiInvoiceItem(
    var name: String?="naamloos",
    var id:Int,
    var unitPrice:Double? = 0.00
)

fun List<ApiInvoiceItem>.asDatabaseModel():Array<DbInvoiceItem>{
    return map {
        DbInvoiceItem(
            id = it.id,
            name = it.name,
            unitPrice = it.unitPrice
        )
    }.toTypedArray()
}