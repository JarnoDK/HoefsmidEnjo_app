package com.enjo.hoefsmidenjo

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.enjo.hoefsmidenjo.databinding.ActivityMainBinding

import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import timber.log.Timber


import android.text.style.TextAppearanceSpan

import android.text.SpannableString
import android.view.Menu
import com.enjo.hoefsmidenjo.screens.login.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.android.material.navigation.NavigationView




class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNav:BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        DomainController()
        // Use Timber
        Timber.tag("MainActivity").i("onCreate Called")

        val navigationView = findViewById<View>(R.id.navView) as NavigationView
        val navController = findNavController(R.id.navHostFragment)


        drawerLayout = this.findViewById(R.id.drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        bottomNav = this.findViewById(R.id.bottomnav)


        NavigationUI.setupWithNavController(navigationView, navController)
        NavigationUI.setupWithNavController(bottomNav, navController)


        val menu: Menu = navigationView.menu

        val categories:Array<Int> = arrayOf(R.id.calendar_category, R.id.client_category, R.id.invoice_category, R.id.item_category)
        for(category:Int in categories){
            var tools: MenuItem = menu.findItem(category)
            val s = SpannableString(tools.title)
            s.setSpan(TextAppearanceSpan(this, R.style.category), 0, s.length, 0)
            tools.title = s
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    bottomNav.visibility = View.INVISIBLE
                }
                else -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    bottomNav.visibility = View.VISIBLE

                    }
                }
        }




    }
}