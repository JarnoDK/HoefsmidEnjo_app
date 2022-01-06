package com.enjo.hoefsmidenjo.database.event

import androidx.room.PrimaryKey
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser

data class DbEvent(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var time: String,
    var client: ApiUser,
    var title:String,
    var location:String

)
