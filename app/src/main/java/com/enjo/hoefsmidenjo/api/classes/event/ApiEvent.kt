package com.enjo.hoefsmidenjo.api.classes.event

import com.enjo.hoefsmidenjo.api.classes.user.ApiUser
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController

/**
 * Inlezen van Event uit backend
*/
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

/**
 * Converteer lijst api event naar een array database event
 * @return Array met database events
*/
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

/**
 * Converteer API event naar database event
 * @return database event
*/
fun ApiEvent.asDatabaseModel():DbEvent{

        return DbEvent (
            id = id,
            client = client.id,
            location = location,
            time = DomainController.instance.getTimeOfString(time),
            title = title
        )
}






