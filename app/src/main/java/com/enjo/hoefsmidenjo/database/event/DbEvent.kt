package com.enjo.hoefsmidenjo.database.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser

@Entity(tableName = "event_table")
data class DbEvent(

    @PrimaryKey(autoGenerate = false)
    var id:Int,
    var time: String,
    var client: Int,
    var title:String,
    var location:String

)
