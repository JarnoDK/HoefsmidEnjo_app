package com.enjo.hoefsmidenjo

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.event.EventDao
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import com.enjo.hoefsmidenjo.database.invoice.InvoiceDao
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import com.enjo.hoefsmidenjo.database.invoiceitem.InvoiceItemDao
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.database.user.UserDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var eventDao: EventDao
    private lateinit var userDao: UserDao
    private lateinit var invoiceDao: InvoiceDao
    private lateinit var invoiceItemDao: InvoiceItemDao

    private lateinit var db: RoomDb


    @Before
    fun createDb() {
        Log.i("before", "running before")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, RoomDb::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        eventDao = db.eventDao
        userDao = db.userDao
        invoiceDao = db.invoiceDao
        invoiceItemDao= db.invoiceItemDao

    }

    private lateinit var user: DbUser
    private lateinit var invoice: DbInvoice
    private lateinit var event:DbEvent

    private lateinit var line: DbInvoiceLine
    private lateinit var line2:DbInvoiceLine

    private lateinit var item: DbInvoiceItem
    private lateinit var item2:DbInvoiceItem


    /**
     * fill db with data to test
     */
    private suspend fun testData(){
        user= DbUser(
            id = -1,
            firstName = "Jhonny",
            lastName = "Test",
            email = "JhonnyTest@test.com",
            phone = "04 99 88 77 66"
        )

        event = DbEvent(
            id = -1,
            title = "Testevent",
            client = -1,
            time = "22/09/2022",
            location = "TestLocation"
        )
        invoice =  DbInvoice(
            id = -2,
            time = "22/05/2022",
            client = -1
        )

        line = DbInvoiceLine(
            id = -2,
            amount = 3,
            item = -2,
            invoiceId = -2
        )
        item = DbInvoiceItem(
            id = -2,
            name = "Item1",
            unitPrice = 3.00
        )

        line2 = DbInvoiceLine(
            id = -3,
            amount = 4,
            item = -3,
            invoiceId = -2
        )
        item2 = DbInvoiceItem(
            id = -3,
            name = "Item2",
            unitPrice = 5.00
        )

        invoiceDao.insert(invoice)
        invoiceItemDao.insert(item)
        invoiceItemDao.insert(item2)

        invoiceDao.insertLine(line)
        invoiceDao.insertLine(line2)
        userDao.insert(user)
        eventDao.insert(event)


    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetEvent() = runBlocking {
        val event = DbEvent(
            id = -1,
            time = "10/12/2022 15:15",
            client = 0,
            title = "Test",
            location = "TestLoc"
        )
        eventDao.insert(event)
        val newEvent = eventDao.getById(-1)
        assertEquals(newEvent.title, event.title)
        assertEquals(newEvent.time, event.time)
        assertEquals(newEvent.location,event.location )

    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() = runBlocking {
        val user = DbUser(
            id = -1,
            firstName = "Tester",
            lastName = "Test",
            role = 0,
            email = "TesterTest@Test.be",
            phone = "04 9288 7259"
        )
        userDao.insert(user)
        val newUser = userDao.getUserById(-1)
        assertEquals(user.firstName, newUser.firstName)
        assertEquals(user.lastName, newUser.lastName)
        assertEquals(user.id,newUser.id )

    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetInvoice() = runBlocking {
        val invoice = DbInvoice(
            id = -1,
            time = "13/01/2023 16:45",
            client = 0
        )
        invoiceDao.insert(invoice)
        val newInvoice = invoiceDao.getInvoiceById(-1)
        assertEquals(newInvoice.id, invoice.id)
        assertEquals(newInvoice.time, invoice.time)
        assertEquals(newInvoice.client,invoice.client )

    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetInvoiceItem() = runBlocking {
        val item = DbInvoiceItem(
            id = -1,
            name = "Item1",
            unitPrice = 5.00
        )
        invoiceItemDao.insert(item)
        val newItem = invoiceItemDao.getById(-1)
        assertEquals(newItem.id, item.id)
        assertEquals(newItem.name, item.name)
        assertEquals(newItem.unitPrice ,item.unitPrice )

    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetInvoiceLine() = runBlocking {
        val line = DbInvoiceLine(
            id = -1,
            amount = 3,
            item = -1,
            invoiceId = -1
        )
        invoiceDao.insertLine(line)
        val newItem = invoiceDao.getLineById(-1)
        assertEquals(newItem.id, line.id)
        assertEquals(newItem.amount, line.amount)
        assertEquals(newItem.item ,line.item )
        assertEquals(newItem.invoiceId ,line.invoiceId )


    }

    @Test
    @Throws(Exception::class)
    fun getTotalPriceOfInvoice() = runBlocking {

        val invoice =  DbInvoice(
            id = -1,
            time = "22/05/2022",
            client = 0
        )

        val line = DbInvoiceLine(
            id = -1,
            amount = 3,
            item = -1,
            invoiceId = -1
        )
        val item = DbInvoiceItem(
            id = -1,
            name = "Item1",
            unitPrice = 3.00
        )
        invoiceDao.insertLine(line)
        invoiceDao.insert(invoice)
        invoiceItemDao.insert(item)

        val calculated = invoiceDao.getTotalAmount(-1)
        assertEquals(calculated,9.00 )


    }


    @Test
    @Throws(Exception::class)
    fun getTotalPriceOfInvoiceWithMoreLines() = runBlocking {
        testData()

        val calculated = invoiceDao.getTotalAmount(-2)
        assertEquals(calculated ,29.00 )


    }


    @Test
    @Throws(Exception::class)
    fun getInvoiceClientAmount_Relation() = runBlocking {
        testData()
        val result = invoiceDao.getById(-2)

        assertEquals(result.client.id,user.id)
        assertEquals(result.invoice.id,invoice.id)
        assertEquals(result.amount , 29.00)

    }


    @Test
    @Throws(Exception::class)
    fun getEventClient_Relation() = runBlocking {
        testData()
        val result = eventDao.getRelEventClientById(-1)

        assertEquals(result.event.id,event.id)
        assertEquals(result.user.id , user.id)
        assertEquals(result.user.firstName,user.firstName)
        assertEquals(result.user.lastName,user.lastName)
        assertEquals(result.event.title,event.title)
    }

    @Test
    @Throws(Exception::class)
    fun deleteUser() = runBlocking {

        testData()
        userDao.insert(user)
        userDao.deleteUser(user.id)

        assertEquals(userDao.getUserById(user.id),null)

    }


}







