package com.example.harrisonwjy.charitree.model.request

class RegisterCM{
    companion object Factory {
        fun create(): RegisterCM = RegisterCM()
    }

    var organization_name: String? = ""
    var UEN: String? = ""


}