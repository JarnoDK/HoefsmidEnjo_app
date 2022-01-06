package com.enjo.hoefsmidenjo.api.classes.user

import com.enjo.hoefsmidenjo.api.classes.enums.RoleType

data class ApiUser(

    var id:Int,
    var firstname:String,
    var lastname:String,
    var role:RoleType,
    var email:String,
    var phone:String,
    var password:String

)
