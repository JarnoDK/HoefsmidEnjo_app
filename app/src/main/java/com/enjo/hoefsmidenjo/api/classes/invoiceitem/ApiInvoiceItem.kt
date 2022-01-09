package com.enjo.hoefsmidenjo.api.classes.invoiceitem

import androidx.room.ColumnInfo
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem

data class ApiInvoiceItem(
    var name: String?="naamloos",
    @ColumnInfo(name = "itemid")
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

fun ApiInvoiceItem.asDatabaseModel():DbInvoiceItem{
    return DbInvoiceItem(
        id = id,
        name = name,
        unitPrice = unitPrice
    )
}