package com.enjo.hoefsmidenjo.domain.domaincontroller

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DomainController {

    companion object {
        lateinit var instance: DomainController
    }

    init{
        instance=this
    }

    fun convertDateTime(input:String):String{
        //2022-01-09T23:15:00.4026798+01:00

        var date = LocalDate.parse(input.substring(0,10))
        var hourMin = LocalTime.parse(input.substring(11,16))
        return "${date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))} $hourMin"
    }
}