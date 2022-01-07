package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.event.ApiEvent
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



interface EventService {

    @GET("/api/event")
    fun getEventAsync(): Deferred<List<ApiEvent>>
}
object EventApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : EventService by lazy {
        Services.retrofit.create(EventService::class.java)
    }
}