package com.enjo.hoefsmidenjo.api.classes.event

import com.enjo.hoefsmidenjo.api.classes.user.ApiUser

data class ApiEvent (
    var id: Int,
    var time: String,
    var clients: List<ApiUser>,
    var title:String,
    var location:String

)
