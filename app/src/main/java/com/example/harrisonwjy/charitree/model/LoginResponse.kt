package com.example.harrisonwjy.charitree.model

class LoginResponse {
    companion object Factory {
        fun create(): LoginResponse = LoginResponse()
    }

    var status: Int = 0
    var user_token: String? = null
}