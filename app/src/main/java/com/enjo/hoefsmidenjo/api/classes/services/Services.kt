package com.enjo.hoefsmidenjo.api.classes.services

import android.util.Patterns
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
/**
 * Connectie met backend
 * Momenteel ngrok om backend lokaal raad te plegen
 */
open class Services {


    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val logger = HttpLoggingInterceptor()
            .apply{level = HttpLoggingInterceptor.Level.BASIC}


        private val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        protected val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASEURL)
            .client(client)
            .build()

    companion object {

        private val BASEURL = "https://0463-2a02-1811-cd1b-600-5511-ad44-33c7-4eae.ngrok.io"

        fun apiIsValid():Boolean{
            return Patterns.WEB_URL.matcher(BASEURL).matches()
        }
    }


}