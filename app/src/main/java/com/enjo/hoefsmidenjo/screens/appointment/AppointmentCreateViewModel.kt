package com.enjo.hoefsmidenjo.screens.appointment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.enjo.hoefsmidenjo.api.classes.event.ApiEvent
import com.enjo.hoefsmidenjo.api.classes.user.asApiUser
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime

class AppointmentCreateViewModel(app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val eventRepo:EventRepository= EventRepository(database)

    var errors = ""
    var users:Array<String> = database.userDao.getAllClientArray().map { s -> "${s.firstName} ${s.lastName}" }.toTypedArray()


    lateinit var selectedUser:DbUser
    var title:String = ""
    var time:String= ""
    var day:String = ""
    var location = ""

    var events:Array<DbEvent> = database.eventDao.getAllEvents()
    var occupiedTimes:Array<LocalDateTime> = events.map {
        s -> s.time.asDateTime()
    }.filter { s -> s.isAfter(LocalDateTime.now()) }
        .toTypedArray()


    fun setSelectedUser(name: String){

        selectedUser= database.userDao.getUserByName(name)
    }
    fun addAppointment():Boolean{
        var check =true
        errors = ""

        if(day.isEmpty()){
            errors+="Dag kan niet leeg zijn\n"
            check = false
        }

        if(location.isEmpty()){

            errors += "Locatie kan niet leeg zijn\n"
            check = false
        }

        if(time.isEmpty()){
            errors+="Tijd kan niet leeg zijn\n"
            check=false
        }

        if(title.isEmpty()){
            errors+="Titel kan niet leeg zijn\n"
            check=false
        }

        var current = "$day $time".asDateTime()

        if(current.isBefore(LocalDateTime.now().minusMinutes(1))){
            errors += "Afspraak kan niet in het verleden liggen\n"
            check=false
        }

        var from = LocalDateTime.now().plusMinutes(30)
        from = from.withSecond(0)
        if(current.isBefore(from.plusMinutes(30))){
            errors += "Afspraak moet minimaal 30 minuten in de toekomst liggen\n"
        }
        var timeCheck = true;
        for (times in occupiedTimes ){
            if(!times.isTimeAvailable(current)){
                timeCheck = false
                break
            }
        }
        if(!timeCheck){
            errors += "Afspraak heeft een overlapping met een andere afspraak\n"
            check = false
        }

        if(check){
            coroutineScope.launch {
                var ev = ApiEvent(
                    id = -1,
                    title = title,
                    client = selectedUser.asApiUser(),
                    time = "$day $time",
                    location = location
                )

                Timber.tag("Added event").i(ev.toString())

                eventRepo.addEvent(ev)
            }
        }

        return check
    }

    fun LocalDateTime.isTimeAvailable(dt:LocalDateTime):Boolean{


        if(dt.isBefore(this.plusMinutes(30)) && dt.isAfter( this.minusMinutes(30))){
            return false
        }
        return true
    }

    fun String.isEmpty():Boolean{
        return this == null || this.trim() == ""
    }
    fun String.asDateTime():LocalDateTime{

        var date:String= this.split(" ")[0]
        var time: String = this.split(" ")[1]

        var datesplit:Array<Int> = date.split("/").map { s -> s.toInt() }.toTypedArray()
        var timesplit:Array<Int> = time.split(":").map { s -> s.toInt() }.toTypedArray()

        return LocalDateTime.of(datesplit[2],datesplit[1],datesplit[0],timesplit[0],timesplit[1])


    }


    override fun onCleared() {
        super.onCleared()

        Timber.tag("ClientViewModel").i("ClientViewModel destroyed")
    }
}