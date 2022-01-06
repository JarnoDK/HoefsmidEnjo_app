package com.enjo.hoefsmidenjo.api.classes.invoice

import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem

data class ApiInvoiceLine(

    var id:Int,
    var amount:Int,
    var item:ApiInvoiceItem
)
