package com.example.harrisonwjy.charitree.repo.interfaces

interface DonationInterface : RepositoryInterface{

    fun getAll(): Any
    fun create(id: Int, item: Any): Any
    fun getCount(item: Any): Any
}