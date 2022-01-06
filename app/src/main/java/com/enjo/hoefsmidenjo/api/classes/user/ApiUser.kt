package com.enjo.hoefsmidenjo.api.classes.user

import com.enjo.hoefsmidenjo.api.classes.enums.RoleType

data class ApiUser(

    var id:Int,
    var firstName:String,
    var lastName:String,
    var role:Int? = 0,
    var email:String?=null,
    var phone:String?=null,
    var password:String?=null

)
