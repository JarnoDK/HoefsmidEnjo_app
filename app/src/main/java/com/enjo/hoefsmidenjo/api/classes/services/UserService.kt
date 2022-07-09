package com.enjo.hoefsmidenjo.api.classes.services

import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

/**
 * Luister naar backend gebruikers
 */
interface UserService {

    /**
     * Verkrijgen lijst met gebruikers
     * @return lijst met API gebruiker
     */
    @GET("/api/user")
    fun getUserAsync(): Deferred<List<ApiUser>>

    /**
     * Verwijderen van gebruiker
     * @param id Gebruiker id dat verwijderd moet worden
     * @return Indien verwijderd true
     */
    @DELETE("/api/user/{id}")
    fun removeUserAsync(@Path("id") id:Int): Deferred<Boolean>

    /**
     * Aanmaken van gebruiker
     * @param user nieuwe gebriker
     * @return aangemaakte Api gebruiker
     */
    @POST("/api/user")
    fun createUserAsync(@Body user: ApiUser): Deferred<ApiUser>

    @GET("/LogIn")
    fun loginUser(@Query("username")username:String, @Query("password")password:String):Call<Boolean>
}

/**
 * Toevoegen gebruiker calls
 */
object UserApi:Services(){
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}