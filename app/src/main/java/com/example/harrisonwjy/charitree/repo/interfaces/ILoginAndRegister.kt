package com.example.harrisonwjy.charitree.repo.interfaces

interface ILoginAndRegister : IRegister, IAuthentication {

    fun getOrgNameByUEN(item: Any): Any

}