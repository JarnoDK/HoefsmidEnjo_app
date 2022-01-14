package com.enjo.hoefsmidenjo.screens.clients.get

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.api.classes.services.Services
import com.enjo.hoefsmidenjo.databinding.FragmentClientGetBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import retrofit2.HttpException


class ClientGetFragment : Fragment() {

    private lateinit var viewModelFactory: ClientGetModelFactory
    private lateinit var viewModel: ClientGetViewModel
    private lateinit var binding: FragmentClientGetBinding

    private lateinit var adapter:ClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentClientGetBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // adapter met onclick event
        adapter = ClientAdapter( ClientListener{
                user ->
                val builder = AlertDialog.Builder(this.requireContext(),R.style.deleteDialog)
                builder.setMessage("Wilt u de klant ${user.firstName} ${user.lastName} verwijderen?")
                    .setCancelable(false)
                    .setPositiveButton("Ja") { _, _ ->
                        if(viewModel.removeUser(user.id)){
                            refreshUsers()
                        }else{
                            AlertDialog
                                .Builder(this.requireContext())
                                .setTitle("Kan klant niet verwijderen")
                                .setMessage("Kan geen verbinding maken met de databank")
                                .show()
                        }
                    }
                    .setNegativeButton("Nee") { dialog, _ ->
                        // Close dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
        })
        // ViewModel
        viewModelFactory = ClientGetModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[ClientGetViewModel::class.java]
        binding.clients.adapter = adapter

        // invullen van correcte data
        refreshUsers()

        // Indien api beschikbaar, hervul database
        if(DomainController.instance.checkForInternet(this.requireContext()) && Services.apiIsValid()){
            try{
                viewModel.reloadUsersFromApi()
            }catch (e: HttpException){ }
        }


        // filter op voornaam wanneer veld bevestigd (enter)
        binding.filterVoornaam.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.firstnamefilter = binding.filterVoornaam.text.toString()
                    refreshUsers()
                    //
                    val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.filterVoornaam.windowToken, 0)
                    return true
                }
                return false
            }
        })

        // filter op achternaam wanneer veld bevestigd (enter)
        binding.filterAchternaam.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.lastnamefilter = binding.filterAchternaam.text.toString()
                    refreshUsers()
                    val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.filterAchternaam.windowToken, 0)
                    return true
                }
                return false
            }
        })

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    /**
     * Herladen lijst met gebruikers dat wordt getoond (filter)
     */
    fun refreshUsers(){

        viewModel.refreshUserList()

        viewModel.users.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

    }



}