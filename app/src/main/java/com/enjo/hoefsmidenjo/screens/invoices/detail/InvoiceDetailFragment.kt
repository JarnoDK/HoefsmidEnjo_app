package com.enjo.hoefsmidenjo.screens.invoices.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceDetailBinding


class InvoiceDetailFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: InvoiceDetailViewModelFactory
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

        // animation
        val navBuilder = NavOptions.Builder()
        navBuilder.setEnterAnim(R.anim.slide_in).setExitAnim(R.anim.slide_out)
            .setPopEnterAnim(R.anim.slide_in).setPopExitAnim(R.anim.slide_out)

        // Binding
        binding = FragmentInvoiceDetailBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // ViewModel
        viewModelFactory = InvoiceDetailViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[InvoiceDetailViewModel::class.java]


        // Ophalen van rekening door meegegeven id
        viewModel.setInvoice(invoiceId)

        val invoice:RelClientInvoiceAmount =  viewModel.invoice

        adapter = InvoiceLineAdapter()
        binding.invoicelines.adapter = adapter

        // invullen rekening lijnen
        viewModel.invoiceItems.observe(viewLifecycleOwner, {

            adapter.submitList(it)
        })

        // datum
        val format = viewModel.invoice.invoice.time.substring(0,10)
        binding.fragmentTitle.text = "Rekening van ${invoice.client.firstName} ${invoice.client.lastName}\n${format}"

        // totale prijs
        binding.invoiceTotalprice.text = "Totale prijs: ${context?.getString(R.string.priceformat)}".format(viewModel.total)

        // back knop
        binding.imgback.setOnClickListener{
            findNavController().navigate(R.id.rekening_bekijken,savedInstanceState, navBuilder.build())
        }


        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }








}