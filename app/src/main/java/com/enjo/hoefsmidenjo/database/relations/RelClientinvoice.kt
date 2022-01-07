package com.enjo.hoefsmidenjo.database.relations

import androidx.room.Embedded
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.user.DbUser


data class RelClientinvoice(

    @Embedded
    var invoice:DbInvoice,
    @Embedded
    var user:DbUser
)