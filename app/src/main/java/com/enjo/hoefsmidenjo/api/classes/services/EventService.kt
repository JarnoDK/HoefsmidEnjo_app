package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.event.ApiEvent
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Luisteren naar event uit backend
 */
interface EventService {

    /**
     * Ophalen events uit backend
     * @return Lijst met Api event
     */
    @GET("/api/Event")
    fun getEventAsync(): Deferred<List<ApiEvent>>

    /**
     * Toevoegen naar backend
     * @param ev Api event
     * @return aangemaakte API event
     */
    @POST("/api/Event")
    fun createEventAsync(@Body ev: ApiEvent):Deferred<ApiEvent>

}

/**
 * Toevoegen calls aan retrofit
 */
object EventApi: Services() {
    val retrofitService : EventService by lazy {
        retrofit.create(EventService::class.java)
    }
}