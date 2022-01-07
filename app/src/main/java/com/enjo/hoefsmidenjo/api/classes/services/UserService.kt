package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface UserService {

    @GET("/api/user")
    fun getUserAsync(): Deferred<List<ApiUser>>



}

object UserApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : UserService by lazy {
        Services.retrofit.create(UserService::class.java)
    }
}