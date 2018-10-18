package com.example.harrisonwjy.charitree.model

class LoginRequest{
    companion object Factory {
        fun create(): LoginRequest = LoginRequest()
    }

    var email: String = ""
    var password: String = ""


}