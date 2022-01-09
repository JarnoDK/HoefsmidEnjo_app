package com.enjo.hoefsmidenjo.api.classes.event

import androidx.lifecycle.Transformations.map
import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.event.DbEvent
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

)


fun List<ApiEvent>.asDatabaseModel():Array<DbEvent>{
        return map{
            DbEvent (
            id = it.id,
            client = it.client.id,
            location = it.location,
            time = getTimeOfString(it.time),
            title = it.title
        )
        }.toTypedArray()
}

fun getTimeOfString(time:String):String{

    val date:LocalDate = LocalDate.parse(time.substring(0,10))
    val hm :LocalTime= LocalTime.parse(time.substring(11,16))
    val dt = LocalDateTime.of(date.year,date.monthValue,date.dayOfMonth,hm.hour,hm.minute)
    val dtstring = dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))

    Timber.tag("Datetime converted").i(dtstring)
    return dtstring
}



