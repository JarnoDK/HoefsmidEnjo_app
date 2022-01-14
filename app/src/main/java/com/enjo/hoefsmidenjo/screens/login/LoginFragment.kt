package com.enjo.hoefsmidenjo.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enjo.hoefsmidenjo.databinding.FragmentLoginBinding


class LoginFragment : Fragment(){

    // ViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

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

        binding.pincodeDisplay.text = ""

        // toevoegen letter
        binding.pincode0.setOnClickListener{ check(0) }
        binding.pincode1.setOnClickListener{ check(1) }
        binding.pincode2.setOnClickListener{ check(2) }
        binding.pincode3.setOnClickListener{ check(3) }
        binding.pincode4.setOnClickListener{ check(4) }
        binding.pincode5.setOnClickListener{ check(5) }
        binding.pincode6.setOnClickListener{ check(6) }
        binding.pincode7.setOnClickListener{ check(7) }
        binding.pincode8.setOnClickListener{ check(8) }
        binding.pincode9.setOnClickListener{ check(9) }
        binding.pincodeBack.setOnClickListener{
            viewModel.removeNumber()
            binding.pincodeDisplay.text = viewModel.pincode
        }

        binding.pincodeOk.setOnClickListener{ check(-1) }



        return binding.root
    }

    /**
     * Inloggen, bij 4'de getal automatische check
     * momenteel hardcoded "1234"
     */
    private fun check(number:Int){
        if(number !=-1){
            viewModel.appendNumber(number)
        }
        if(viewModel.pincode.length >=4){
                if(viewModel.validatePin()){
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

                }else{
                    AlertDialog
                        .Builder(this.requireContext())
                        .setTitle("Inloggen mislukt")
                        .setMessage("Ongeldige pincode, probeer opnieuw")
                        .show()
                    viewModel.clearPincode()
                }
        }else if(number == -1){
            if(viewModel.validatePin()){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

            }else{
                AlertDialog
                    .Builder(this.requireContext())
                    .setTitle("Inloggen mislukt")
                    .setMessage("Ongeldige pincode, probeer opnieuw")
                    .show()
                viewModel.clearPincode()
            }
        }
        binding.pincodeDisplay.text = viewModel.pincode

    }


}