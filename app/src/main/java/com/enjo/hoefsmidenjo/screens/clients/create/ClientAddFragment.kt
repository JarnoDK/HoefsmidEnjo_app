package com.enjo.hoefsmidenjo.screens.clients.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.databinding.FragmentClientAddBinding
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

        // voeg gebruiker toe
        binding.buttonAdduser.setOnClickListener{
                addUser()
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    /**
     * Initialiseer viewmodels met inputs
     * Toevoegen van gebruiker
     */
    fun addUser(){

        // check voor apparaat online
        if(DomainController.instance.checkForInternet(this.requireContext())){
            // toekennen variabelen
            viewModel.firstname = binding.firstname.text.toString()
            viewModel.lastname = binding.lastname.text.toString()
            viewModel.email = binding.Email.text.toString()
            viewModel.telephone = binding.telefoon.text.toString()
            // controleer of aanmaken is gelukt, success melding indien geslaagd, anders foutmelding met fouten
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

                // Legen van velden na aanmaken klant
                viewModel.resetValues()
                binding.firstname.text.clear()
                binding.lastname.text.clear()
                binding.telefoon.text.clear()
                binding.Email.text.clear()

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