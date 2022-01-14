package com.enjo.hoefsmidenjo.domain.domaincontroller

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DomainController {

    /**
     * Domaincontroller toegankelijk maken over hele applicatie
     */
    companion object {
        lateinit var instance: DomainController
    }

    /**
     * Toekennen van domaincontroller aan instance
     */
    init{
        instance=this
    }

    /**
     * Converteer datetime uit api naar formaat dd/MM/yy HH:mm
     * @param time Ontvangen formaat
     * @return string van datum en tijd in formaat dd/MM/yyyy HH:mm
     */
    fun getTimeOfString(time:String):String{

        val date:LocalDate = LocalDate.parse(time.substring(0,10))
        val hm :LocalTime= LocalTime.parse(time.substring(11,16))
        val dt = LocalDateTime.of(date.year,date.monthValue,date.dayOfMonth,hm.hour,hm.minute)
        return dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))

    }

    /**
     * Controleer of internet beschikbaar is
     */
    fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }

    }
}