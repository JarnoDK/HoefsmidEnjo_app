package com.enjo.hoefsmidenjo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.event.EventDao
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import com.enjo.hoefsmidenjo.database.invoice.InvoiceDao
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import com.enjo.hoefsmidenjo.database.invoiceitem.InvoiceItemDao
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.database.user.UserDao

@Database(entities = [

    DbEvent::class,
DbInvoice::class,
DbUser::class,
DbInvoiceItem::class,
DbInvoiceLine::class,
], version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract val eventDao: EventDao
    abstract val invoiceDao: InvoiceDao
    abstract val invoiceItemDao: InvoiceItemDao
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getInstance(context: Context): RoomDb {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java,
                    "HoefsmidEnjo_Roomdb"
                ).fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}