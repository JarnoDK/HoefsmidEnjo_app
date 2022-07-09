package com.enjo.hoefsmidenjo.screens.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enjo.hoefsmidenjo.R
import com.enjo.hoefsmidenjo.databinding.FragmentLoginBinding


class LoginFragment : Fragment(){

    // ViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPref:SharedPreferences
    // Binding
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentLoginBinding.inflate(layoutInflater)

        // ViewModel
        viewModelFactory = LoginViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        //binding.loginViewModel = viewModel
        binding.lifecycleOwner = this


        var preference = activity?.getPreferences(Context.MODE_PRIVATE)
        if(preference != null){
            sharedPref = preference
            if(sharedPref.getBoolean("isLoggedIn",false)){
                // Login
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

            }
        }

        binding.btnLogin.setOnClickListener{
            if(logIn()){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

            }
        }

        return binding.root
    }

    private fun logIn():Boolean {


            if(sharedPref.getBoolean("isLoggedIn",false)){
                // Login
                return true
            }

            var errors = ""
        if (binding.txtUsername.equals(null) || binding.txtUsername.equals("")) {
            errors += "${R.string.usernameEmpty}\n"
        }
        if (binding.txtPassword.equals(null) || binding.txtPassword.equals("")) {
            errors += "${R.string.PasswordEmpty}\n"
        }

        if (viewModel.logIn(
                binding.txtUsername.text.toString(),
                binding.txtPassword.text.toString()
            ) ) {


            with (sharedPref.edit()) {
                putString("username", binding.txtUsername.text.toString())
                putString("password", binding.txtPassword.text.toString())
                putBoolean("isLoggedIn", true)

                apply()
            }

        }
        return true
    }


}