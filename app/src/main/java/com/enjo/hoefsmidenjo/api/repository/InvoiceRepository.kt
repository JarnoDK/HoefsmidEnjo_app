package com.enjo.hoefsmidenjo.api.repository

import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoice
import com.enjo.hoefsmidenjo.api.classes.services.InvoiceApi
import timber.log.Timber

class InvoiceRepository {

    suspend fun refreshInvoices(Invoices: List<ApiInvoice>) {

        // call all calendars
        val invoices = InvoiceApi.retrofitService.getInvoiceAsync().await()
            //database.CalendarDatabaseDao.insertAll(*calendars)
            Timber.i("end suspend")
    }
}
