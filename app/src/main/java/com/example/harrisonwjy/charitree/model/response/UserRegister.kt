package com.example.harrisonwjy.charitree.model.response

class UserRegister {
    companion object Factory {
        fun create(): Login = Login()
    }

    var status: Int? = 0
    var errors: Errors? = null
}
