package com.enjo.hoefsmidenjo.screens.invoices.create

import android.R.attr
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceCreateBinding
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceDetailBinding
import timber.log.Timber
import android.R.attr.country
import android.widget.TableRow
import com.enjo.hoefsmidenjo.database.user.DbUser


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
        binding.lifecycleOwner = this

        return binding.root
    }

    fun addItem(){

        var name:String = binding.invoiceItemname.selectedItem.toString()
        var amount :Int = binding.invoiceamount.text.toString().toInt()

        var row:TableRow =  viewModel.addItem(name,amount, this.requireContext())

        Timber.tag("data").i("name: $name , amount:$amount")

        binding.items.addView(row)
    }






}