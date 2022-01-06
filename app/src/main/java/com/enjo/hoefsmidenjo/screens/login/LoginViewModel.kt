package com.example.k2_kolveniershof_android.screens.login

import androidx.lifecycle.ViewModel
import timber.log.Timber

class LoginViewModel:ViewModel() {

    var Pincode = ""

    val TestPincode = "1234"

    fun AppendNumber(number:Int){
        Pincode += number
    }

    fun RemoveNumber(){
        if(Pincode!=""){
            Pincode = Pincode.substring(0,Pincode.length-1)
        }

    }
    fun ClearPincode(){
        Pincode = ""
    }

    fun ValidatePin():Boolean{
        if(Pincode != ""){
            if(Pincode == TestPincode){
                return true
            }
        }
        return false
    }
    init {
        Timber.tag("LoginViewModel").i("LoginViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag("LoginViewModel").i("LoginViewModel destroyed")
    }
}