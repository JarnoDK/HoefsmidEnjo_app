package com.enjo.hoefsmidenjo.api.classes.event

import androidx.lifecycle.Transformations.map
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.event.DbEvent

data class ApiEvent (
    var id: Int,
    var time: String,
    var client: ApiUser,
    var title:String,
    var location:String

)


fun List<ApiEvent>.asDatabaseModel():Array<DbEvent>{
        return map{
            DbEvent (
            id = it.id,
            client = it.client.id,
            location = it.location,
            time = it.time,
            title = it.title
        )
        }.toTypedArray()
}



