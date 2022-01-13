package com.enjo.hoefsmidenjo.database.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Database queries voor gebruikers
 */
@Dao
interface UserDao {

    /**
     * toevoegen van meerdere gebruikers
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: DbUser)

    /**
     * Toevoegen van een enkele gebruiker
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: DbUser)

    /**
     * Ophalen van alle klanten uit databank
     * @return Livedata met lijst van klanten
     */
    @Query("SELECT * FROM users where role==0")
    fun getAllClients():LiveData<List<DbUser>>

    /**
     * Ophalen van alle klanten uit databank
     * @return Array met lijst van klanten
     */
    @Query("SELECT * FROM users where role==0")
    fun getAllClientArray():Array<DbUser>

    /**
     * Klanten filteren op voornaam en achternaam
     * @param firstname voornaam bevat gegeven tekst
     * @param lastname achternaam bevat gegeven tekst
     * @return LiveDate met lijst van gefilterde gebruikers
     */
    @Query("select * from users where firstName LIKE '%' || :firstname || '%' and lastName LIKE '%'||:lastname||'%'")
    fun getAllFilteredClients(firstname:String,lastname:String):LiveData<List<DbUser>>

    /**
     * Verwijderen van gebruiker
     * @param id Gebruiker id
     */
    @Query("delete from users where userid == :id")
    fun deleteUser(id:Int)

    /**
     * get user by his id
     * @param id user id
     * @return DbUser
     */
    @Query("Select * from users where userid == :id")
    fun getUserById(id:Int):DbUser

    /**
     * gebruiker van enkele gebruiker op basis van volledige naam
     */
    @Query("SELECT * FROM users WHERE (firstName ||' '||lastName) == :name")
    fun getUserByName(name: String):DbUser

}