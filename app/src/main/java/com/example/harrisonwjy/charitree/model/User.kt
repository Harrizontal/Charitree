package com.example.harrisonwjy.charitree.model

class User {

    companion object Factory {
        fun create(): User = User()
    }

    var status: Int = 0
    var user_token: String =""
}
