package com.example.harrisonwjy.charitree

interface UserRepository {
    fun giveHello(): String
    fun login(email: String, password: String): String
}