package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Luisteren naar rekening items uit backend
 */
interface InvoiceItemService {

    /**
     * Verkrijgen lijst met rekening items uit backend
     * @return lijst API rekening item
     */
    @GET("/api/invoiceItem")
    fun getInvoiceItemAsync(): Deferred<List<ApiInvoiceItem>>

    /**
     * Toevoegen van rekening item
     * @param item Api rekening item
     * @return Api rekening item
     */
    @POST("/api/invoiceItem")
    fun createInvoiceItemAsync(@Body item:ApiInvoiceItem):Deferred<ApiInvoiceItem>

}

/**
 * Toevoegen service aan retrofit
 */
object InvoiceItemApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : InvoiceItemService by lazy {
        Services.retrofit.create(InvoiceItemService::class.java)
    }
}