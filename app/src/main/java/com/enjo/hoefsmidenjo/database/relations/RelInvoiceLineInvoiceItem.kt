package com.enjo.hoefsmidenjo.database.relations

import androidx.room.Embedded
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem

/**
 * Relatie tussen invoiceline en invoiceitem
 */
data class RelInvoiceLineInvoiceItem(
    @Embedded
    var invoiceLine:DbInvoiceLine,
    @Embedded
    var item:DbInvoiceItem
)
