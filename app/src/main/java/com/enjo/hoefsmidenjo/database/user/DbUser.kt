package com.enjo.hoefsmidenjo.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enjo.hoefsmidenjo.api.classes.enums.RoleType

@Entity(tableName = "users")
data class DbUser(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userid")
    var id:Int,
    var firstName:String,
    var lastName:String,
    var role:Int? = 0,
    var email:String,
    var phone:String,
    var password:String?=null,
    var pincode:String?=null

)
