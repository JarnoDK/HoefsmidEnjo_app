package com.enjo.hoefsmidenjo.screens.invoices.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceCreateBinding
import timber.log.Timber
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.TableRow
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
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

        Timber.tag("Invoice").i("InvoiceCreateFragment loaded")

        // Fills users in selector
        val userAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, viewModel.users)
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.invoiceclient.adapter = userAdapter


        // Fills items in selector
        val itemListAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, viewModel.items)
        itemListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.invoiceItemname.adapter = itemListAdapter

        binding.btnvoegitemtoe.setOnClickListener{
            addItem()
        }


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


        val now = LocalTime.now()
        binding.hourpicker.setIs24HourView(true)

        binding.btnaddinvoice.setOnClickListener{
            addInvoice()
        }

        binding.lifecycleOwner = this

        return binding.root
    }

    fun addItem(){

        var name:String = binding.invoiceItemname.selectedItem.toString()
        var amount :Int = binding.invoiceamount.text.toString().toInt()

        var row:TableRow =  viewModel.addItem(name,amount, this.requireContext())

        binding.items.addView(row)
    }

    fun addInvoice(){

        var timepicker = binding.hourpicker
        viewModel.time = "${timepicker.hour}:${timepicker.minute}"

        viewModel.addInvoice(binding.invoiceclient.selectedItem.toString())

    }


}