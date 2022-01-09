package com.enjo.hoefsmidenjo.database.event

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg event :DbEvent)

    @Query("SELECT * FROM event_table ORDER BY title DESC")
    fun getAllEventsLive(): LiveData<List<DbEvent>>

    @Query("SELECT * FROM event_table ev join users us on ev.client == us.userid where time LIKE :date|| '%' ORDER BY title DESC ")
    fun getAllEventsOfDateLive(date:String): LiveData<List<RelUserEvent>>
}