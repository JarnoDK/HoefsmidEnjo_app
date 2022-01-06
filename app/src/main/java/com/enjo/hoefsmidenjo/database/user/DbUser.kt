package com.enjo.hoefsmidenjo.database.user

import androidx.room.PrimaryKey
import com.enjo.hoefsmidenjo.api.classes.enums.RoleType

data class DbUser(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var firstName:String,
    var lastName:String,
    var role:Int? = 0,
    var email:String?=null,
    var phone:String?=null,
    var password:String?=null

)
