package com.enjo.hoefsmidenjo.database.event

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent

/**
 * Toevoegen queries voor objecten
 */
@Dao
interface EventDao {

    /**
     * Toevoegen van meerdere database events aan de database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg event :DbEvent)

    /**
     * Leeg events database
     */
    @Query("DELETE FROM event_table")
    fun clearEvents()
    /**
     * Toevoegen van een enkele database even aan de database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event :DbEvent)

    /**
     * Get event by id
     * @param id eventId
     * @return DbEvent with given id
     */
    @Query("SELECT * FROM event_table WHERE eventid == :id")
    suspend fun getById(id:Int):DbEvent

    /**
     * Get event and client by event id
     * @param id event id
     * @return relation client-event by event id
     */
    @Query("SELECT * FROM event_table JOIN users on userid == client WHERE eventid == :id")
    suspend fun getRelEventClientById(id:Int):RelUserEvent
    /**
     * Ophalen events uit event tabel gesorteerd op titel
     * @return Livedata van lijst met database events
     */
    @Query("SELECT * FROM event_table ORDER BY title DESC")
    fun getAllEventsLive(): LiveData<List<DbEvent>>

    /**
     * Ophalen events uit event tabel van gegeven datum
     * @param date datum waarop gesorteerd moet worden
     * @return Livedata van lijst met database en user
     */
    @Query("SELECT * FROM event_table ev join users us on ev.client == us.userid where time LIKE :date|| '%' ORDER BY title DESC ")
    fun getAllEventsOfDateLive(date:String): LiveData<List<RelUserEvent>>

    /**
     * Ophalen lijst van event uit database
     * @return array met events
     */
    @Query("SELECT * FROM event_table ORDER BY time")
    fun getAllEvents():Array<DbEvent>
}