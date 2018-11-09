package com.example.harrisonwjy.charitree.repo.interfaces

interface AddressInterface : RepositoryInterface {

    fun get(): Any
    fun create(item: Any): Any
}