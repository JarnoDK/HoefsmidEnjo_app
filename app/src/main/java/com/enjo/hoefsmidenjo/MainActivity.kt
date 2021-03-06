package com.enjo.hoefsmidenjo


import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.enjo.hoefsmidenjo.databinding.ActivityMainBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import com.enjo.hoefsmidenjo.screens.login.LoginFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Binding
        DomainController()

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Use Timber
        Timber.tag("MainActivity").i("onCreate Called")

        val navigationView = findViewById<View>(R.id.navView) as NavigationView
        navController = findNavController(R.id.navHostFragment)


        drawerLayout = this.findViewById(R.id.drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        bottomNav = this.findViewById(R.id.bottomnav)


        NavigationUI.setupWithNavController(navigationView, navController)
        NavigationUI.setupWithNavController(bottomNav, navController)


        val menu: Menu = navigationView.menu

        // drawer nav categorie??n
        val categories: Array<Int> = arrayOf(
            R.id.calendar_category,
            R.id.client_category,
            R.id.invoice_category,
            R.id.item_category
        )
        // drawer nav design
        for (category: Int in categories) {
            val tools: MenuItem = menu.findItem(category)
            val s = SpannableString(tools.title)
            s.setSpan(TextAppearanceSpan(this, R.style.category), 0, s.length, 0)
            tools.title = s
        }




        // indien login scherm, schakel navigatie uit, anders schakel navigatie in
        navController.addOnDestinationChangedListener { _, destination, _  ->


            when (destination.id) {
                R.id.loginFragment -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    bottomNav.visibility = View.INVISIBLE
                }
                R.id.logout -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    bottomNav.visibility = View.INVISIBLE
                }

                else -> {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }


        val logout: View = findViewById(R.id.profileFragment)
        // klik op profiel, toont scherm om uit te loggen
        logout.setOnClickListener {
            // show popup when user clicks account button
            val builder = AlertDialog.Builder(this, R.style.deleteDialog)
            builder.setMessage("Wilt u uitloggen?")
                .setCancelable(false)
                .setPositiveButton("Ja") { _, _ ->
                    logout()
                }
                .setNegativeButton("Nee") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

    }

    /**
     * Navigeer naar login scherm
     */
    private fun logout() {
        var preference = getPreferences(Context.MODE_PRIVATE)
        if(preference != null){


            with (preference.edit()) {
                putString("username", null)
                putString("password", null)
                putBoolean("isLoggedIn", false)

                apply()
                Log.d("logout","User logged out")
            }
        }

        navController.navigate(R.id.logout)
        val navController = findNavController(R.id.navHostFragment)
        navController.navigate(R.id.loginFragment)
    }
}


