package com.example.harrisonwjy.charitree.repo.interfaces

interface ICampaign : IRegister, IAuthentication {

    fun showAll(): Any
    fun showAllByCMSession(): Any
    fun showAddress(): Any
    fun createAddress(item: Any): Any
    fun createDonation(id: Int,item: Any): Any
    fun showAllUserDonations() : Any
    fun getOrgNameByUEN(item: Any): Any
    fun getItems(): Any
    fun createCampaign(item: Any): Any
    fun getListOfDonors(item: Any): Any
    fun getDonationByDID(item: Any): Any
    fun changeStatusByDID(id: Int,item: Any): Any

}