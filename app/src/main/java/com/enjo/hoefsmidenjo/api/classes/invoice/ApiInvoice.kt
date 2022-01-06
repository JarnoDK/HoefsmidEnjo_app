package com.enjo.hoefsmidenjo.api.classes.invoice

import com.enjo.hoefsmidenjo.api.classes.user.ApiUser

data class ApiInvoice(

    var id:Int,
    var client: ApiUser,
    var time:String,
    var invoiceLines:List<ApiInvoiceLine>

)
