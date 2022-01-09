package com.enjo.hoefsmidenjo.screens.items.get

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
import com.enjo.hoefsmidenjo.databinding.FragmentItemGetBinding
import timber.log.Timber


class ItemGetFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: ItemGetModelFactory
    private lateinit var viewModel: ItemGetViewModel
    private lateinit var binding: FragmentItemGetBinding

    private lateinit var adapter:ItemAdapter
    // Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        refreshItems()
        binding.items.adapter = adapter


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

        Timber.tag("Home").i("Ite get fragment loaded")

        binding.lifecycleOwner = this

        return binding.root
    }

    fun refreshItems(){

        viewModel.refreshItems()

        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }


}