package com.enjo.hoefsmidenjo.database.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: DbUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: DbUser)

    @Query("SELECT * FROM users where role==0")
    fun getAllClients():LiveData<List<DbUser>>

    @Query("select * from users where firstName LIKE '%' || :firstname || '%' and lastName LIKE '%'||:lastname||'%'")
    fun getAllFilteredClients(firstname:String,lastname:String):LiveData<List<DbUser>>

    @Query("delete from users where userid == :id")
    fun deleteUser(id:Int)

}