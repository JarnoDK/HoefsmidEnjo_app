package com.enjo.hoefsmidenjo.screens.login

import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {

    var pincode = ""

    private val testPincode = "1234"

    /**
     * voeg nummer toe aan pincode
     */
    fun appendNumber(number:Int){
        pincode += number
    }

    /**
     * verwijder laatste element van pincode
     */
    fun removeNumber(){
        if(pincode!=""){
            pincode = pincode.substring(0,pincode.length-1)
        }

    }

    /**
     * Leeg pincode
     */
    fun clearPincode(){
        pincode = ""
    }

    /**
     * Controleerd pincode
     * @return true indien pincode correct
     */
    fun validatePin():Boolean{
        if(pincode != ""){

            if(pincode == testPincode){
                return true
            }
        }
        return false
    }



}