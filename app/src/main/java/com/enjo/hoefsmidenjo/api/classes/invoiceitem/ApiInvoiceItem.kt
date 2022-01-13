package com.enjo.hoefsmidenjo.api.classes.invoiceitem

import androidx.room.ColumnInfo
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem

/**
 * Api van invoice item
 */
data class ApiInvoiceItem(
    var name: String?="naamloos",
    @ColumnInfo(name = "itemid")
    var id:Int,
    var unitPrice:Double? = 0.00
)

/**
 * Converteer lijst van rekening items naar array van database items
 * @return Array van database items
 */
fun List<ApiInvoiceItem>.asDatabaseModel():Array<DbInvoiceItem>{
    return map {
        DbInvoiceItem(
            id = it.id,
            name = it.name,
            unitPrice = it.unitPrice
        )
    }.toTypedArray()
}

/**
 * Converteer Api rekening item naar database rekening item
 * @return database rekening item
 */
fun ApiInvoiceItem.asDatabaseModel():DbInvoiceItem{
    return DbInvoiceItem(
        id = id,
        name = name,
        unitPrice = unitPrice
    )
}
/**
 * Converteer database rekening item naar API rekening item
 * @return Api rekening item
 */
fun DbInvoiceItem.asApiModel():ApiInvoiceItem{
    return ApiInvoiceItem(
        name = name,
        id = id,
        unitPrice = unitPrice
    )
}