package com.example.harrisonwjy.charitree.repo

import com.example.harrisonwjy.charitree.model.Request

interface IRegister : IRepository {

    fun register (item: Any): Any

}