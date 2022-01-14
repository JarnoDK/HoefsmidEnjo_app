package com.enjo.hoefsmidenjo.screens.items.get

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.api.classes.services.Services
import com.enjo.hoefsmidenjo.databinding.FragmentItemGetBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import timber.log.Timber


class ItemGetFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: ItemGetModelFactory
    private lateinit var viewModel: ItemGetViewModel
    private lateinit var binding: FragmentItemGetBinding

    private lateinit var adapter:ItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentItemGetBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application


        adapter = ItemAdapter()
        // ViewModel
        viewModelFactory = ItemGetModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemGetViewModel::class.java]
        binding.items.adapter = adapter

        // Indien api beschikbaar, hervul database
        if(DomainController.instance.checkForInternet(this.requireContext()) && Services.APIIsValid){
            viewModel.reloadItemsFromApi()
        }else{
            Timber.tag("FAIL").i("Api is niet beschikbaar")
        }

        // vult lijst met items
        refreshItems()

        // filter op naam
        binding.filtername.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.itemname = binding.filtername.text.toString()
                    refreshItems()
                    val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.filtername.windowToken, 0)
                    return true
                }
                return false
            }
        })


        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    fun refreshItems(){

        viewModel.refreshItems()

        viewModel.items.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

    }


}