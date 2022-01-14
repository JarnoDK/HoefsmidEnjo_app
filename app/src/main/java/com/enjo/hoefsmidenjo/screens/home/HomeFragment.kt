package com.enjo.hoefsmidenjo.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enjo.hoefsmidenjo.databinding.FragmentHomeBinding
import timber.log.Timber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


class HomeFragment : Fragment() {

    // ViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private var adapter = EventAdapter()

    // Binding
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Binding
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // ViewModel
        viewModelFactory = HomeViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        Timber.tag("Home").i("ItemCreateFragment loaded")
        binding.planning.adapter = adapter

        // Set days at top
        binding.Today.text = viewModel.date.dayOfMonth.toString()
        binding.tomorrowDate.text = viewModel.date.plusDays(1).dayOfMonth.toString()
        binding.tomorrowDate.text = viewModel.date.plusDays(-1).dayOfMonth.toString()

        update(0)

        // update on click
        binding.viewTomorrow.setOnClickListener{
            update(1)
        }
        binding.viewYesterday.setOnClickListener{
            update(1,true)
        }



        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    /**
     * Refresh day
     * @param day offset compared to current selected date
     * @param negative whether or not to go back or forward
     */
    private fun update(day:Int,negative:Boolean=false){

        var dt :LocalDate = viewModel.date

        if(negative){
            viewModel.date = dt.minusDays(day.toLong())

        }else{
            viewModel.date = dt.plusDays(day.toLong())

        }
         dt = viewModel.date


        updateToday(dt)
        updateTomorrow(dt)
        updateYesterday(dt)

        viewModel.refreshList()
        viewModel.events.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }

    /**
     * Set previousdate
     * @param current date
     */
    private fun updateYesterday(dt:LocalDate){
        binding.yesterdayDate.text = dt.plusDays(-1).dayOfMonth.toString()
        binding.yesterdayDay.text = dt.minusDays(1).dayOfWeek.getDisplayName(TextStyle.FULL , Locale("nl","NL")).substring(0,2).uppercase()

    }
    /**
     * Set today
     * @param current date
     */
    private fun updateToday(dt:LocalDate){
        binding.Today.text = dt.dayOfMonth.toString()
        binding.todayDay.text = dt.dayOfWeek.getDisplayName(TextStyle.FULL , Locale("nl","NL")).substring(0,2).uppercase()
        binding.date.text =  " " + dt.dayOfMonth + " " + dt.month.getDisplayName(TextStyle.FULL , Locale("nl","NL"))

    }
    /**
     * Set tomorrow
     * @param current date
     */
    private fun updateTomorrow(dt:LocalDate){

        binding.tomorrowDay.text = dt.plusDays(1).dayOfWeek.getDisplayName(TextStyle.FULL , Locale("nl","NL")).substring(0,2).uppercase()
        binding.tomorrowDate.text = dt.plusDays(1).dayOfMonth.toString()

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}