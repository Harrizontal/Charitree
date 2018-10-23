package com.example.harrisonwjy.charitree.repo

import android.arch.lifecycle.LiveData
import com.example.harrisonwjy.charitree.model.LoginResponse
import com.example.harrisonwjy.charitree.model.Request
import java.util.*

interface IAuthentication  : IRepository{
//    fun giveHello(): String
//    fun login(email: String, password: String): String
    fun verify(item: Any): Any

}

