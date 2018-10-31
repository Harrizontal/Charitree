package com.example.harrisonwjy.charitree.repo

interface IAuthentication  : IRepository{
//    fun giveHello(): String
//    fun login(email: String, password: String): String
    fun verify(item: Any): Any

}

