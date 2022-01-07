package com.enjo.hoefsmidenjo.api.classes.user

import com.enjo.hoefsmidenjo.api.classes.enums.RoleType
import com.enjo.hoefsmidenjo.database.user.DbUser

data class ApiUser(

    var id:Int,
    var firstName:String,
    var lastName:String,
    var role:Int? = 0,
    var email:String?=null,
    var phone:String?=null,
    var password:String?=null

)

fun List<ApiUser>.asDatabaseModel():Array<DbUser>{
    return map {
        DbUser(
            firstName = it.firstName,
            lastName = it.lastName,
            id = it.id,
            email = it.email,
            phone = it.phone,
            password = it.password,
            role = it.role
        )
    }.toTypedArray()
}
