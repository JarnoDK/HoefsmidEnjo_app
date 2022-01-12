package com.enjo.hoefsmidenjo.screens.items.create

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.databinding.FragmentHome3Binding
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceitemAddBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import timber.log.Timber
import java.lang.Exception
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


class ItemCreateFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: ItemCreateViewModelFactory
    private lateinit var viewModel: ItemCreateViewModel

    // Binding
    private lateinit var binding: FragmentInvoiceitemAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        Timber.tag("Home").i("ItemCreateFragment loaded")

        binding.additem.setOnClickListener{
            viewModel.name = binding.itemname.text.toString()
            viewModel.price = binding.Prijs.parseToDouble()
            if(DomainController.instance.checkForInternet(this.requireContext())) {
                addItem()

            }else {
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Geen verbinding")
                    .setMessage("Kan geen verbinding maken tot databank")
                    .show()
            }
        }


        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    private fun addItem(){

        if(viewModel.createInvoiceItem()){
            AlertDialog
                .Builder(this.requireContext())
                .setTitle("item aangemaakt")
                .setMessage("Het item ${viewModel.name} is aangemaakt")
                .show()
            binding.itemname.text.clear()
            binding.Prijs.text.clear()

            viewModel.name = ""
            viewModel.price = -1.00
        }else{
            AlertDialog
                .Builder(this.requireContext())
                .setTitle("Item aanmaken mislukt")
                .setMessage(viewModel.errors)
                .show()
        }


    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }


}

fun EditText.parseToDouble() :Double{
    var text = this.text.toString()

    try {
        return text.toDouble()
    }catch (ex:Exception){
        return -1.00
    }

}


