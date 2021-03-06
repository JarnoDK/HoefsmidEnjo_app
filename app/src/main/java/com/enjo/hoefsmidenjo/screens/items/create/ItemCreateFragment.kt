package com.enjo.hoefsmidenjo.screens.items.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.api.classes.services.Services
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceitemAddBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController


class ItemCreateFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: ItemCreateViewModelFactory
    private lateinit var viewModel: ItemCreateViewModel

    // Binding
    private lateinit var binding: FragmentInvoiceitemAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentInvoiceitemAddBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // ViewModel
        viewModelFactory = ItemCreateViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemCreateViewModel::class.java]


        // Item toevoegen
        binding.additem.setOnClickListener {

            addItem()


        }


        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    private fun addItem() {


        if (DomainController.instance.checkForInternet(this.requireContext()) && Services.apiIsValid()) {
            viewModel.name = binding.itemname.text.toString()
            viewModel.price = binding.Prijs.parseToDouble()
            if (viewModel.createInvoiceItem()) {
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("item aangemaakt")
                    .setMessage("Het item ${viewModel.name} is aangemaakt")
                    .show()
                binding.itemname.text.clear()
                binding.Prijs.text.clear()

                viewModel.name = ""
                viewModel.price = -1.00
            } else {
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Item aanmaken mislukt")
                    .setMessage(viewModel.errors)
                    .show()
            }
        } else {
            AlertDialog
                .Builder(this.requireContext())
                .setTitle("Geen verbinding")
                .setMessage("Kan geen verbinding maken met internet/databank")
                .show()
        }


    }


}

fun EditText.parseToDouble(): Double {
    val text = this.text.toString()

    return try {
        text.toDouble()
    } catch (ex: Exception) {
        -1.00
    }

}


