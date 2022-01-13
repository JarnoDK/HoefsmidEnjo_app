package com.enjo.hoefsmidenjo.api.classes.invoice

import androidx.room.ColumnInfo
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine

/**
* Api van rekening lijnen
*/
data class ApiInvoiceLine(

    @ColumnInfo(name = "invoielineid")
    var id:Int,
    var amount:Int,
    var item:ApiInvoiceItem
)

/**
 * Converteer lijst van API rekeningen lijn naar Array met database reking lijnen
 * @param invoiceId Rekening id van welke rekening je de lijnen wilt converteren
 * @return Array met database invoice lijnen
 */
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
