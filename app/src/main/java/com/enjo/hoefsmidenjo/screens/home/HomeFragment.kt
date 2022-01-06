package com.enjo.hoefsmidenjo.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.databinding.FragmentHomeBinding
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HomeFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    // Binding
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentHomeBinding.inflate(layoutInflater)
        
        // ViewModel
        viewModelFactory = HomeViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        Timber.tag("Home").i("HomeFragment loaded")

        binding.datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->

            viewModel.date = LocalDate.of(year,monthOfYear+1,dayOfMonth)
            Timber.tag("Date").i(viewModel.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        }

        binding.lifecycleOwner = this

        return binding.root
    }


}