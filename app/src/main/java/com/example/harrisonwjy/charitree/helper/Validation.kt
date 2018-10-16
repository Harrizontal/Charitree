package com.example.harrisonwjy.charitree.helper

import android.support.design.widget.TextInputLayout
import android.util.Log
import android.widget.EditText

interface Validation{

    fun isValidEmail(textField: EditText, inputLayout: TextInputLayout, message: CharSequence) : Boolean{

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(textField.text.toString()).matches()){
            Log.e("InputValidateShowError","Email is valid")
            inputLayout.isErrorEnabled = false
        }else{
            Log.e("InputValidateShowError","Email is not valid")
            inputLayout.error = message
            //textField.requestFocus()
            return false
        }
        return true
    }


    fun isValidInput(textField: EditText, inputLayout: TextInputLayout, message: CharSequence): Boolean{
        if(textField.text.toString().isNotEmpty()){
            Log.e("LoginFragment","Password is not empty")
            inputLayout.isErrorEnabled = false
        }else{
            Log.e("LoginFragment","Password is empty")
            inputLayout.error = message
            textField.requestFocus()
            return false
        }
        return true
    }
}