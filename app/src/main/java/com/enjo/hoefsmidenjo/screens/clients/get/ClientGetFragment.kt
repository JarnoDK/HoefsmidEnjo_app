package com.enjo.hoefsmidenjo.screens.clients.get

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.MainActivity
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.databinding.FragmentClientGetBinding
import timber.log.Timber


class ClientGetFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: ClientGetModelFactory
    private lateinit var viewModel: ClientGetViewModel
    private lateinit var binding: FragmentClientGetBinding

    private lateinit var adapter:ClientAdapter
    // Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentClientGetBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application


        adapter = ClientAdapter( ClientListener{
                user ->
            //Toast.makeText(context, "${userID}", Toast.LENGTH_SHORT).show()

                // show popup when user clicks account button
                val builder = AlertDialog.Builder(this.requireContext(),R.style.deleteDialog)
                builder.setMessage("Wilt u de klant ${user.firstName} ${user.lastName} verwijderen?")
                    .setCancelable(false)
                    .setPositiveButton("Ja") { _, _ ->
                        viewModel.removeUser(user.id)
                        refreshUsers()
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

        refreshUsers()
        binding.clients.adapter = adapter


        binding.filterVoornaam.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.firstnamefilter = binding.filterVoornaam.text.toString()
                    refreshUsers()
                    val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.filterVoornaam.windowToken, 0)
                    return true
                }
                return false
            }
        })

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
        Timber.tag("Home").i("HomeFragment loaded")

        binding.lifecycleOwner = this

        return binding.root
    }

    fun refreshUsers(){

        viewModel.refreshUserList()

        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }


}