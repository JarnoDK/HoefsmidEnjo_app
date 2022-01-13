package com.enjo.hoefsmidenjo.database.event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database model van event naar tabel event_table
 */
@Entity(tableName = "event_table")
data class DbEvent(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "eventid")
    var id:Int,
    var time: String,
    var client: Int,
    var title:String,
    var location:String

)
