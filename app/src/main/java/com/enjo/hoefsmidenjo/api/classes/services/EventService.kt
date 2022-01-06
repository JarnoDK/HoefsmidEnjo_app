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


private const val BASE_URL = " https://2f57-2a02-1811-cd1b-600-58a9-878f-c5e2-8fd0.ngrok.io"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor()
    .apply{level = HttpLoggingInterceptor.Level.BASIC}

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface EventService {

    @GET("/api/event")
    fun getEventAsync(): Deferred<List<ApiEvent>>
}
object EventApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : EventService by lazy {
        retrofit.create(EventService::class.java)
    }
}