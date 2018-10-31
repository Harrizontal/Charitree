package com.example.harrisonwjy.charitree.model.response

class LoginResponse {
    companion object Factory {
        fun create(): LoginResponse = LoginResponse()
    }

    var httpStatus: Int? = 0
    var user_token: String? = null
    var message: String? = null
    var errors: ArrayList<String>? = ArrayList()
    var isValidResponse: Boolean? = null
}