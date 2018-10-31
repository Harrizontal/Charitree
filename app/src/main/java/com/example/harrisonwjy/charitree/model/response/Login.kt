package com.example.harrisonwjy.charitree.model.response

class Login {
    companion object Factory {
        fun create(): Login = Login()
    }

    var status: Int? = 0
    var user_token: String? = null
    var errors: Errors? = null
}

