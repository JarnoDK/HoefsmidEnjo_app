package com.enjo.hoefsmidenjo.screens.invoices.get

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.databinding.FragmentHome3Binding
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceListBinding
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceListItemBinding
import timber.log.Timber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


class InvoiceFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: InvoiceViewModelFactory
    private lateinit var viewModel: InvoiceViewModel
    private var adapter = InvoiceAdapter()

    // Binding
    private lateinit var binding: FragmentInvoiceListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentInvoiceListBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // ViewModel
        viewModelFactory = InvoiceViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[InvoiceViewModel::class.java]

        Timber.tag("Home").i("ItemCreateFragment loaded")
        viewModel.refreshList()
        adapter = InvoiceAdapter()

        binding.invoices.adapter = adapter

        viewModel.invoices.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


        binding.lifecycleOwner = this

        return binding.root
    }




}