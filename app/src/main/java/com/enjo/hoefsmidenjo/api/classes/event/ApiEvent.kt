package com.enjo.hoefsmidenjo.api.classes.event

import androidx.lifecycle.Transformations.map
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

data class ApiEvent (
    var id: Int,
    var time: String,
    var client: ApiUser,
    var title:String,
    var location:String

){

    override fun toString(): String {
        return "id:$id time:$time title:$title location:$location\nclient: $client"
    }
}


fun List<ApiEvent>.asDatabaseModel():Array<DbEvent>{
        return map{
            DbEvent (
            id = it.id,
            client = it.client.id,
            location = it.location,
            time = DomainController.instance.getTimeOfString(it.time),
            title = it.title
        )
        }.toTypedArray()
}

fun ApiEvent.asDatabaseModel():DbEvent{

        return DbEvent (
            id = id,
            client = client.id,
            location = location,
            time = DomainController.instance.getTimeOfString(time),
            title = title
        )
}






