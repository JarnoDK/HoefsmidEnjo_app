package com.enjo.hoefsmidenjo.database.relations

import androidx.room.Embedded
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.user.DbUser

/**
 * Relatie tussen klant, rekening en aantal
 */
data class RelClientInvoiceAmount (

    @Embedded
    var invoice:DbInvoice,
    @Embedded
    var client:DbUser,
    var amount: Double
    )