package com.enjo.hoefsmidenjo.api.classes.user

import com.enjo.hoefsmidenjo.database.user.DbUser

data class ApiUser(

    var id:Int,
    var firstName:String,
    var lastName:String,
    var role:Int? = 0,
    var email:String="",
    var phone:String?="",
    var password:String?=null,
    var pincode:String?=null

)
{
    override fun toString(): String {
        return "id: ${id}\nfirstname:${firstName}\nlastname:${lastName}\nrole:$role\nemail: $email\nphone:$phone\npassword:$password\nPincode:$pincode"
    }
}

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
            pincode= it.pincode
        )
    }.toTypedArray()
}

fun DbUser.asApiUser():ApiUser{

    return ApiUser(
        id = id,
        firstName = firstName,
        lastName=lastName,
        email=email,
        password = password?:"",
        pincode = pincode?:"",
        phone = phone,
        role = role
    )
}
fun ApiUser.asDatabaseModel():DbUser{
    return DbUser(
        id = id,
        firstName = firstName,
        lastName=lastName,
        email=email,
        password = password?:"",
        pincode = pincode?:"",
        phone = phone.orEmpty(),
        role = role
    )
}


