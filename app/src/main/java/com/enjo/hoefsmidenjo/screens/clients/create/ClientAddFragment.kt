package com.enjo.hoefsmidenjo.screens.clients.create

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.MainActivity
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.databinding.FragmentClientAddBinding
import com.enjo.hoefsmidenjo.databinding.FragmentClientGetBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import timber.log.Timber


class ClientAddFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: ClientAddModelFactory
    private lateinit var viewModel: ClientAddViewModel
    private lateinit var binding: FragmentClientAddBinding

    // Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentClientAddBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application


        // ViewModel
        viewModelFactory = ClientAddModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[ClientAddViewModel::class.java]

        Timber.tag("Home").i("ItemCreateFragment loaded")
        binding.buttonAdduser.setOnClickListener{

            viewModel.firstname = binding.firstname.text.toString()
            viewModel.lastname = binding.lastname.text.toString()
            viewModel.email = binding.Email.text.toString()
            viewModel.telephone = binding.telefoon.text.toString()

            if(DomainController.instance.checkForInternet(this.requireContext())) {
                addUser()

            }else {
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Geen verbinding")
                    .setMessage("Kan geen verbinding maken tot databank")
                    .show()
            }



        }

        binding.lifecycleOwner = this

        return binding.root
    }


    fun addUser(){

            if (!viewModel.addUser()) {
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Kan klant niet maken")
                    .setMessage(viewModel.errors)
                    .show()

            } else {
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Klant aangemaakt")
                    .setMessage("De klant ${viewModel.firstname} ${viewModel.lastname} is aangemaakt")
                    .show()
                viewModel.resetValues()
                binding.firstname.text.clear()
                binding.lastname.text.clear()
                binding.telefoon.text.clear()
                binding.Email.text.clear()

            }



    }



}