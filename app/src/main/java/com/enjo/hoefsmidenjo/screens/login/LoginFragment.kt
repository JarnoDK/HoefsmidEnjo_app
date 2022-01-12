package com.enjo.hoefsmidenjo.screens.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enjo.hoefsmidenjo.MainActivity
import com.enjo.hoefsmidenjo.databinding.FragmentLoginBinding
import com.example.k2_kolveniershof_android.screens.login.LoginViewModel
import com.example.k2_kolveniershof_android.screens.login.LoginViewModelFactory
import timber.log.Timber


class LoginFragment : Fragment(){

    // ViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    // Binding
    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        binding.pincode0.setOnClickListener{ view -> Check(0) }
        binding.pincode1.setOnClickListener{ view -> Check(1) }
        binding.pincode2.setOnClickListener{ view -> Check(2) }
        binding.pincode3.setOnClickListener{ view -> Check(3) }
        binding.pincode4.setOnClickListener{ view -> Check(4) }
        binding.pincode5.setOnClickListener{ view -> Check(5) }
        binding.pincode6.setOnClickListener{ view -> Check(6) }
        binding.pincode7.setOnClickListener{ view -> Check(7) }
        binding.pincode8.setOnClickListener{ view -> Check(8) }
        binding.pincode9.setOnClickListener{ view -> Check(9) }
        binding.pincodeBack.setOnClickListener{
            viewModel.RemoveNumber()
            binding.pincodeDisplay.text = viewModel.Pincode

        }



        return binding.root
    }

    fun Check(number:Int){
        viewModel.AppendNumber(number)
        if(viewModel.Pincode.length >=4){
            // add check for valid user

                if(viewModel.ValidatePin()){
                    Timber.tag("Login").i("Successful login")

                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

                }else{
                    Timber.tag("Login").i("Failed login")

                }
            // relocate
            viewModel.ClearPincode()
        }
        binding.pincodeDisplay.text = viewModel.Pincode

    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

}