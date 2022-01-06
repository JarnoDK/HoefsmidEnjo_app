package com.enjo.hoefsmidenjo.database.relations

import androidx.room.Embedded
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.user.DbUser

data class Rel_User_Event(

    @Embedded
    var user:DbUser,
    @Embedded
    var event:DbEvent

)
