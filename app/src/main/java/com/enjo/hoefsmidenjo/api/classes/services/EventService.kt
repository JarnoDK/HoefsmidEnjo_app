package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.event.ApiEvent
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventService {

    @GET("/api/Event")
    fun getEventAsync(): Deferred<List<ApiEvent>>

    @POST("/api/Event")
    fun createEventAsync(@Body ev: ApiEvent):Deferred<ApiEvent>

}
object EventApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : EventService by lazy {
        Services.retrofit.create(EventService::class.java)
    }
}