package com.enjo.hoefsmidenjo.screens.invoices.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceDetailBinding
import timber.log.Timber


class InvoiceDetailFragment() : Fragment() {

    // ViewModel
    private lateinit var ViewModelFactory: InvoiceDetailViewModelFactory
    private lateinit var viewModel: InvoiceDetailViewModel
    private lateinit var adapter: InvoiceLineAdapter
    private var invoiceId:Int = -1
    // Binding
    private lateinit var binding: FragmentInvoiceDetailBinding

    /**
     * toevoegen van id door middel van argument
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("invoiceid").let{ invId ->
            if (invId != null) {
                this.invoiceId = invId
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentInvoiceDetailBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // ViewModel
        ViewModelFactory = InvoiceDetailViewModelFactory(application)
        viewModel = ViewModelProvider(this, ViewModelFactory)[InvoiceDetailViewModel::class.java]


        // Ophalen van rekening door meegegeven id
        viewModel.setInvoice(invoiceId)

        var invoice:RelClientInvoiceAmount =  viewModel.invoice

        adapter = InvoiceLineAdapter()
        binding.invoicelines.adapter = adapter

        // invullen rekening lijnen
        viewModel.invoiceItems.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it)
        })

        // datum
        var format = viewModel.invoice.invoice.time.substring(0,10)
        binding.fragmentTitle.text = "Rekening van ${invoice.client.firstName} ${invoice.client.lastName}\n${format}"

        // totale prijs
        binding.invoiceTotalprice.text = "Totale prijs: %.2f €".format(viewModel.total)

        // back knop
        binding.imgback.setOnClickListener{
            findNavController().navigate(R.id.rekening_bekijken)
        }


        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }






}