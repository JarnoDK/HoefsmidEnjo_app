package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface InvoiceItemService {

    @GET("/api/invoiceItem")
    fun getInvoiceItemAsync(): Deferred<List<ApiInvoiceItem>>

    @POST("/api/invoiceItem")
    fun createInvoiceItemAsync(@Body item:ApiInvoiceItem):Deferred<ApiInvoiceItem>

}
object InvoiceItemApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : InvoiceItemService by lazy {
        Services.retrofit.create(InvoiceItemService::class.java)
    }
}