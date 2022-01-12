package com.enjo.hoefsmidenjo.screens.appointment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.databinding.FragmentAppointmentCreateBinding
import com.enjo.hoefsmidenjo.databinding.FragmentClientGetBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AppointmentCreateFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: AppointmentCreateModelFactory
    private lateinit var viewModel: AppointmentCreateViewModel
    private lateinit var binding: FragmentAppointmentCreateBinding

    // Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentAppointmentCreateBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application



        // ViewModel
        viewModelFactory = AppointmentCreateModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AppointmentCreateViewModel::class.java]

        var now = LocalDateTime.now()

        viewModel.day = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        val userListAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, viewModel.users)
        userListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.appointmentclient.adapter = userListAdapter

        viewModel.setSelectedUser(binding.appointmentclient.selectedItem.toString())

        binding.appointmentdate.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this.requireContext(),
                R.style.datepicker, DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->


                var dt = LocalDate.of(year,monthOfYear,dayOfMonth)
                viewModel.day =  dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

                Timber.tag("Selected date").i(viewModel.day)
            }, now.year, now.monthValue, now.dayOfMonth)
            datePickerDialog.show()
        }

        binding.appointmenttime.setIs24HourView(true)
        viewModel.time = now.format(DateTimeFormatter.ofPattern("HH:mm"))

        binding.appointmentconfirm.setOnClickListener{
            addAppointment()
        }

        Timber.tag("Home").i("ItemCreateFragment loaded")

        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    fun addAppointment(){

        if(DomainController.instance.checkForInternet(this.requireContext())){

            viewModel.time = "${binding.appointmenttime.hour}:${binding.appointmenttime.minute}"
            viewModel.location = binding.appointmentlocation.text.toString()
            viewModel.setSelectedUser(binding.appointmentclient.selectedItem.toString())
            viewModel.title = binding.appointmenttitle.text.toString()
            if(viewModel.addAppointment()){
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Afspraak toegevoegd")
                    .setMessage("De afspraak met ${binding.appointmentclient.selectedItem} op ${viewModel.day} ${viewModel.time} is succesvol toegevoegd")
                    .show()
            }
            else{
                AlertDialog
                .Builder(this.requireContext())
                    .setTitle("Afspraak niet toegevoegd")
                    .setMessage(viewModel.errors)
                    .show()
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
        binding.unbind()
        super.onDestroy()
    }


}