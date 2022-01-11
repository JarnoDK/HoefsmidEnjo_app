package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoice
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.ApiInvoiceItem
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST


interface InvoiceService {

    @GET("/api/invoice")
    fun getInvoiceAsync(): Deferred<List<ApiInvoice>>

    @POST("/api/invoice")
    fun createInvoiceAsync(inv:ApiInvoice):Deferred<ApiInvoice>



}

object InvoiceApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : InvoiceService by lazy {
        Services.retrofit.create(InvoiceService::class.java)
    }
}