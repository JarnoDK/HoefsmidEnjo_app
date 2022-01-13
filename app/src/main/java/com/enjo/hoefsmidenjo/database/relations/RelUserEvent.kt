package com.enjo.hoefsmidenjo.database.relations

import androidx.room.Embedded
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.user.DbUser

/**
 * Relatie tussen klant en event
 */
data class RelUserEvent(

    @Embedded
    var user:DbUser,
    @Embedded
    var event:DbEvent

)
