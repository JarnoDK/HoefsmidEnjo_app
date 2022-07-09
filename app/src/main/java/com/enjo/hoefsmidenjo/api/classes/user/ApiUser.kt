package com.enjo.hoefsmidenjo.api.classes.user

import com.enjo.hoefsmidenjo.database.user.DbUser

/**
 * Api model van gebruiker
 */
data class ApiUser(

    var id:Int,
    var firstName:String,
    var lastName:String,
    var role:Int? = 0,
    var email:String="",
    var phone:String?="",
    var password:String?=null,
    var username:String?=null

)
{
    override fun toString(): String {
        return "id: ${id}\nfirstname:${firstName}\nlastname:${lastName}\nrole:$role\nemail: $email\nphone:$phone\npassword:$password\nPincode:$username"
    }
}

/**
 * Converteer lijst met API gebruikers naar array met database gebruikers
 * @return Array met database gebruikers
 */
fun List<ApiUser>.asDatabaseModel():Array<DbUser>{
    return map {
        DbUser(
            firstName = it.firstName,
            lastName = it.lastName,
            id = it.id,
            email = it.email,
            phone = it.phone.orEmpty(),
            password = it.password,
            role = it.role,
            username= it.username
        )
    }.toTypedArray()
}

/**
 * Converteer een enkele database gebruiker naar api gebruiker
 * @return API gebruiker
 */
fun DbUser.asApiUser():ApiUser{

    return ApiUser(
        id = id,
        firstName = firstName,
        lastName=lastName,
        email=email,
        password = password?:"",
        username = username?:"",
        phone = phone,
        role = role
    )
}

/**
 * Converteer een enkele API gebruiker naar database gebruiker
 * @return database gebruiker
 */
fun ApiUser.asDatabaseModel():DbUser{

    return DbUser(
        id = id,
        firstName = firstName,
        lastName=lastName,
        email=email,
        password = password?:"",
        username = username?:"",
        phone = phone.orEmpty(),
        role = role
    )
}


