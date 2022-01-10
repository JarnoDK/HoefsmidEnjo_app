package com.enjo.hoefsmidenjo.screens.invoices.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceListBinding
import timber.log.Timber


class InvoiceDetailFragment : Fragment() {

    // ViewModel
    private lateinit var ViewModelFactory: InvoiceDetailViewModelFactory
    private lateinit var viewModel: InvoiceDetailViewModel

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
        ViewModelFactory = InvoiceDetailViewModelFactory(application)
        viewModel = ViewModelProvider(this, ViewModelFactory)[InvoiceDetailViewModel::class.java]

        Timber.tag("Home").i("ItemCreateFragment loaded")
        viewModel.refreshList()




        binding.lifecycleOwner = this

        return binding.root
    }




}