package com.enjo.hoefsmidenjo.screens.invoices.get

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceListBinding
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class InvoiceFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: InvoiceViewModelFactory
    private lateinit var viewModel: InvoiceViewModel
    private lateinit var adapter:InvoiceAdapter

    // Binding
    private lateinit var binding: FragmentInvoiceListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentInvoiceListBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // ViewModel
        viewModelFactory = InvoiceViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[InvoiceViewModel::class.java]

        Timber.tag("Home").i("ItemCreateFragment loaded")
        viewModel.refreshList()
        adapter = InvoiceAdapter(
            InvoiceDetailListener{
                invoice ->

                val bundle= Bundle()
                bundle.putInt("invoiceid",invoice.invoice.id)
                findNavController().navigate(R.id.detailfragment, bundle)
        })

        binding.invoices.adapter = adapter

        var current:LocalDate = LocalDate.now()
        viewModel.date = current.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        binding.invoiceDate.text = viewModel.date
        binding.imgClear.visibility = View.VISIBLE


        binding.invoiceDate.setOnClickListener{



            val datePickerDialog = DatePickerDialog(this.requireContext(),R.style.datepicker, DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->


                viewModel.current = LocalDate.of(year,monthOfYear,dayOfMonth)
                viewModel.date =  viewModel.current.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                binding.invoiceDate.text = viewModel.date
                refreshList()
                binding.imgClear.visibility = View.VISIBLE

            }, viewModel.current.year, viewModel.current.monthValue, viewModel.current.dayOfMonth)
            datePickerDialog.show()

        }

        binding.imgClear.setOnClickListener{
            viewModel.date = ""
            binding.invoiceDate.text = ""

            binding.imgClear.visibility = View.INVISIBLE
            viewModel.current = LocalDate.now()
            refreshList()
        }


        binding.firstname.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.firstname = binding.firstname.text.toString()
                    refreshList()
                    val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.firstname.windowToken, 0)
                    return true
                }
                return false
            }
        })

        binding.invoiceLastname.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.lastname = binding.invoiceLastname.text.toString()
                    refreshList()
                    val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.invoiceLastname.windowToken, 0)
                    return true
                }
                return false
            }
        })


        refreshList()



        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    fun refreshList(){

        viewModel.firstname = binding.firstname.text.toString()
        viewModel.lastname = binding.invoiceLastname.text.toString()

        viewModel.refreshList()

        viewModel.invoices.observe(viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }




}