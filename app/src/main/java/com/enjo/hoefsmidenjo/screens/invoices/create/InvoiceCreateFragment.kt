package com.enjo.hoefsmidenjo.screens.invoices.create

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceCreateBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class InvoiceCreateFragment() : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: InvoiceCreateViewModelFactory
    private lateinit var viewModel: InvoiceCreateViewModel

    // Binding
    private lateinit var binding: FragmentInvoiceCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentInvoiceCreateBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // ViewModel
        viewModelFactory = InvoiceCreateViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[InvoiceCreateViewModel::class.java]

        if(DomainController.instance.checkForInternet(this.requireContext())){
            viewModel.reloadInvoicesFromApi()
        }

        // vult mogelijke gebruikers
        val userAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, viewModel.users)
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.invoiceclient.adapter = userAdapter


        // vult mogelijke items
        val itemListAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, viewModel.items)
        itemListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.invoiceItemname.adapter = itemListAdapter

        binding.btnvoegitemtoe.setOnClickListener{
            addItem()
        }


        // datum
        binding.invoicedate.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this.requireContext(),R.style.datepicker, DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->


                viewModel.current = LocalDate.of(year,monthOfYear,dayOfMonth)
                viewModel.date =  viewModel.current.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                binding.invoicedate.text = viewModel.date

                Timber.tag("Selected date").i(viewModel.date)
            }, viewModel.current.year, viewModel.current.monthValue, viewModel.current.dayOfMonth)
            datePickerDialog.show()
        }


        // tijd
        binding.hourpicker.setIs24HourView(true)

        // Voegt rekening toe
        binding.btnaddinvoice.setOnClickListener{
            addInvoice()
        }

        // deelt tabel met viewmodel
        viewModel.table = binding.items

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    /**
     * Voegt rekening item toe
     */
    fun addItem(){
        // variabelen
        var name:String = binding.invoiceItemname.selectedItem.toString()
        var amountstring = binding.invoiceamount.text.toString()
        var amount :Int = -1

        // controleert of item geldig is, indien true, voegt rij toe aan tabel, anders toont een foutmelding
        if(viewModel.isValid(name,amountstring)){
            amount = amountstring.toInt()
            var row:TableRow =  viewModel.addItem(name,amount, this.requireContext())
            binding.items.addView(row)
        }else{
            AlertDialog
                .Builder(this.requireContext())
                .setTitle("Item niet toegevoegd")
                .setMessage(viewModel.errors)
                .show()
            viewModel.errors = ""
        }




    }

    /**
     * Voegt rekening toe indien geldig, toont bericht nadien
     */
    fun addInvoice(){

        // controleert internet, indien geen internet , geeft melding
        if(DomainController.instance.checkForInternet(this.requireContext())){

            // variabelen initialiseren
            var timepicker = binding.hourpicker
            viewModel.time = "${timepicker.hour}:${timepicker.minute}"

            //voegt invoice toe en leeft bepaalde verlden indien true, anders geeft foutmelding
            if(viewModel.addInvoice(binding.invoiceclient.selectedItem.toString())){
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Rekening aangemaakt")
                    .setMessage("De rekening voor ${binding.invoiceclient.selectedItem} is succesvol toegevoegd")
                    .show()

                binding.InvoiceLineItem.text = ""
                binding.invoiceamount.text.clear()

            }else{
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Rekening niet aangemaakt")
                    .setMessage("De rekening bevat geen items")
                    .show()
            }


        }else{
            AlertDialog
                .Builder(this.requireContext())
                .setTitle("Geen verbinding")
                .setMessage("Kan geen verbinding maken met internet")
                .show()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }


}