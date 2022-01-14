package com.enjo.hoefsmidenjo.screens.invoices.create

import android.app.Application
import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoice
import com.enjo.hoefsmidenjo.api.classes.invoice.ApiInvoiceLine
import com.enjo.hoefsmidenjo.api.classes.invoiceitem.asApiModel
import com.enjo.hoefsmidenjo.api.classes.user.asApiUser
import com.enjo.hoefsmidenjo.database.RoomDb
import com.enjo.hoefsmidenjo.database.invoice.DbInvoice
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.repository.InvoiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.firstOrNull
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.sorted
import kotlin.collections.toList
import kotlin.collections.toTypedArray

class InvoiceCreateViewModel(app: Application): AndroidViewModel(app){


    private val database = RoomDb.getInstance(app.applicationContext)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var users: Array<String> = database.userDao.getAllClientArray().map { s -> "${s.firstName} ${s.lastName}" }.sorted().toTypedArray()
    var items: Array<String> = database.invoiceItemDao.GetAllArray().map { s -> "${s.name}"}.sorted().toTypedArray()

    lateinit var selectedUser:DbUser

    var current = LocalDate.now()
    var date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    var time:String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

    var invoiceRepo:InvoiceRepository= InvoiceRepository(database)

    lateinit var invoice:DbInvoice
    var lines:MutableList<ApiInvoiceLine> = mutableListOf()
    var rows:MutableMap<String,TableRow> = mutableMapOf()

    var errors:String = ""

    lateinit var table:TableLayout


    /**
     * Maakt nieuw item aan voor de rekening
     * @return tabel rij met waarde van item en verwijder knop
     */
    fun addItem(name:String , amount:Int, context:Context ):TableRow{


        // Aanmaken rows en items in kolommen
        val row = TableRow(context)
        val itemName = TextView(context)
        val am = TextView(context)
        val total = TextView(context)
        val delete = ImageView(context)

        // Zet foto van imageview
        delete.setImageResource(R.drawable.trashcan)
        // imageview zet klikbaar
        delete.isClickable = true

        // onclick voor waarden in rij, verwijderd lijn
        delete.setOnClickListener{
            removeInvoiceLine(name)
        }

        // toevoegen stijl van items
        itemName.setTextAppearance(R.style.tablerowtext)
        am.setTextAppearance(R.style.tablerowtext)
        total.setTextAppearance(R.style.tablerowprice)

        itemName.gravity = Gravity.CENTER_HORIZONTAL
        am.gravity = Gravity.CENTER_HORIZONTAL
        total.gravity = Gravity.CENTER


        // naam van item naar item
        val item:DbInvoiceItem = database.invoiceItemDao.getByName(name)

        // zet layout breedte naar wrapcontent
        var layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            )

        // waarde van lijnen invullen
        var unitprice:Double = item.unitPrice?:0.00
        var result:Double = unitprice*amount

        //  view elementen invullen met correcte tekst
        itemName.text = name
        am.text = "$amount"
        total.text = "%.2f €".format(result)

        // item breedte limiteren
        itemName.maxWidth = 150

        // toevoegen view elementen aan row
        row.addView(itemName, layoutParams)
        row.addView(am, layoutParams)
        row.addView(total,layoutParams)
        row.addView(delete,layoutParams)

        // views in row verticaal centreren
        row.gravity = Gravity.CENTER_VERTICAL

        // toevoegen lijnen aan lijst
        lines.add(
            ApiInvoiceLine(
                amount = amount,
                id = -1,
                item = item.asApiModel()
            )
        )
        // opslaan rij per naam van item (aangezien deze uniek moet zijn)
        rows[name] = row
        return row
    }

    /**
     * Verwijderen item van lijst met items en uit tabel
     * @param name naam van item
     */
    private fun removeInvoiceLine(name: String){
        Timber.tag("Removing $name" )

        // verwijder uit tabel (zicht)
        if(rows[name] != null){
            table.removeView(rows[name])
            rows.remove(name)
            // verwijder van rekening lijnen (benodigdheid in rekening)
            var invoiceLine = lines.firstOrNull{s -> s.item.name == name}
            if(invoiceLine != null){
                lines.remove(invoiceLine)
            }
        }
    }


    /**
     * Controleert of item reeds in de lijst zit
     * @param name naam van item
     * @return true indien item in lijst
     */
    fun itemExists(name:String):Boolean{
        for(item in lines){
            if(item.item.name == name){
                return true
            }
        }
        return false
    }

    /**
     * Voeg rekening toe
     * @param client geselecteerde klant
     */
    fun addInvoice(client:String):Boolean{

        // tijd naar correct formaat
        var dt = current
        var timestring = time.split(":")
        var tm = LocalTime.of(timestring[0].toInt(),timestring[1].toInt())
        var datetime :LocalDateTime = LocalDateTime.of(dt.year,dt.monthValue,dt.dayOfMonth,tm.hour,tm.minute)
        var dtstring = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))

        var check = true

        // controleerd of rekening daadwerkelijk items heeft
        if(lines.size == 0){
            errors += "kan geen lege rekening sturen\n"
            check=false
        }


        if(!check){
            return false
        }

        var inv  = ApiInvoice(
            client = database.userDao.getUserByName(client).asApiUser(),
            id = -1,
            time = dtstring,
            invoiceLines = lines
        )

        coroutineScope.launch {
            invoiceRepo.addInvoice(inv)
            errors = ""

        }

        return check
    }

    fun reloadInvoicesFromApi(){
        coroutineScope.launch {
            var invRepo = InvoiceRepository(database)
            invRepo.InsertFromApi()
        }
    }


    override fun onCleared() {
        super.onCleared()
        Timber.tag("LoginViewModel").i("LoginViewModel destroyed")
    }

    /**
     * controleerd of een item kan worden toegevoegd
     */
    fun isValid(name: String, amountstring: String): Boolean {
        var check = true
        var amount:Int = -1
        // controle of amount een getal is , indien wel, neemt getal, anders getal blijft -1, check is false en melding toegevoegd
        if(!amountstring.matches(Regex("[\\d]+"))){

            errors += "Aantal moet een getal zijn\n"
            check = false
        }else{
            amount = amountstring.toInt()
        }

        // Controle op aantal minimum 1
        if(amount < 1){
            errors += "Aantal moet minimum 1 bedragen\n"
            check=false
        }
        // controle of naam niet leef is
        if(name == null || name.trim().equals("")){
            errors += "Naam kan niet leeg zijn\n"
            check=false
        }

        // controle of item uniek is
        if(itemExists(name)){
            errors +="Item zit reeds in de rekening\n"
            check = false
        }



        return check
    }

}