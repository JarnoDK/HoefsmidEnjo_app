package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoice
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface InvoiceService {

    @GET("/api/invoice")
    fun getInvoiceAsync(): Deferred<List<ApiInvoice>>

    @POST("/api/invoice")
    fun createInvoiceAsync(@Body inv:ApiInvoice):Deferred<ApiInvoice>



}

object InvoiceApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : InvoiceService by lazy {
        Services.retrofit.create(InvoiceService::class.java)
    }
}