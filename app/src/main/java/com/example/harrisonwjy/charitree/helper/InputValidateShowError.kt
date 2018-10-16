package com.example.harrisonwjy.charitree.helper

import android.content.res.Resources
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.R.id.input_password
import com.example.harrisonwjy.charitree.R.id.input_email
import kotlinx.android.synthetic.main.fragment_login.view.*


class InputValidateShowError constructor(private val input_field: EditText, val input_layout :TextInputLayout, val message : CharSequence) : TextWatcher,Validation {
    //constructor(input_field: EditText,input_layout: TextInputLayout,message: Int) : this(input_field,input_layout)


    override fun afterTextChanged(p0: Editable?) {
        return
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        return
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        when(input_field.id){
            R.id.input_email -> isValidEmail(input_field,input_layout,message)
            R.id.input_password -> isValidInput(input_field,input_layout,message)
        }

    }
}




