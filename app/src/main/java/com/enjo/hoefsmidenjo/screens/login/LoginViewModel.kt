package com.example.k2_kolveniershof_android.screens.login

import androidx.lifecycle.ViewModel
import timber.log.Timber

class LoginViewModel:ViewModel() {

    var Pincode = ""

    val TestPincode = "1234"

    /**
     * voeg nummer toe aan pincode
     */
    fun AppendNumber(number:Int){
        Pincode += number
    }

    /**
     * verwijder laatste element van pincode
     */
    fun RemoveNumber(){
        if(Pincode!=""){
            Pincode = Pincode.substring(0,Pincode.length-1)
        }

    }

    /**
     * Leeg pincode
     */
    fun ClearPincode(){
        Pincode = ""
    }

    /**
     * Controleerd pincode
     * @return true indien pincode correct
     */
    fun ValidatePin():Boolean{
        if(Pincode != ""){
            if(Pincode == TestPincode){
                return true
            }
        }
        return false
    }


    override fun onCleared() {
        super.onCleared()
    }
}