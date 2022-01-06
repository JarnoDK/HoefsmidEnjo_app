package com.enjo.hoefsmidenjo.api.classes.invoiceitem

data class ApiInvoiceItem(
    var name: String?="naamloos",
    var id:Int,
    var unitPrice:Double? = 0.00
)