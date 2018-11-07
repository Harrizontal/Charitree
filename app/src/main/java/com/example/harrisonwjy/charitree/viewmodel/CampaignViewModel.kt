package com.example.harrisonwjy.charitree.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.harrisonwjy.charitree.model.Campaign
import com.example.harrisonwjy.charitree.model.request.ChangeStatusDonationRequest
import com.example.harrisonwjy.charitree.model.request.CreateCampaignRequest
import com.example.harrisonwjy.charitree.model.request.CreateDonationRequest
import com.example.harrisonwjy.charitree.model.request.GetAddressRequest
import com.example.harrisonwjy.charitree.model.response.*
import com.example.harrisonwjy.charitree.repo.CampaignRepo

class CampaignViewModel : ViewModel(){


    fun getListOfCampaigns(repo: CampaignRepo): LiveData<GetCampaignsResponse> {
        return repo.showAll() as LiveData<GetCampaignsResponse>
    }

    fun getListOfCampaignsByCMSession(repo: CampaignRepo): LiveData<GetCampaignsByCMSession>{
        return repo.showAllByCMSession() as LiveData<GetCampaignsByCMSession>
    }

    fun getAddress(repo: CampaignRepo): LiveData<GetAddressResponse>{
        return repo.showAddress() as LiveData<GetAddressResponse>
    }

    fun createAddress(repo: CampaignRepo, request: GetAddressRequest): LiveData<GetAddressResponse>{
        return repo.createAddress(request) as LiveData<GetAddressResponse>
    }

    fun createDonation(repo: CampaignRepo,campaignId: Int, request: CreateDonationRequest): LiveData<CreateDonationResponse>{
        Log.e("CampaignViewModel","createdDonation called")
        return repo.createDonation(campaignId,request) as LiveData<CreateDonationResponse>
    }

    fun getItems(repo: CampaignRepo): LiveData<GetItemsResponse>{
        return repo.getItems() as LiveData<GetItemsResponse>
    }

    fun createCampaign(repo: CampaignRepo, request: CreateCampaignRequest): LiveData<CreateCampaignResponse>{
        return repo.createCampaign(request) as LiveData<CreateCampaignResponse>
    }

    fun getListOfDonors(repo: CampaignRepo, campaignId: Int): LiveData<GetListOfDonationsByCIDResponse>{
        return repo.getListOfDonors(campaignId) as LiveData<GetListOfDonationsByCIDResponse>
    }

    fun getDonationByDID(repo: CampaignRepo, donationId: Int): LiveData<GetDonationByDonationIDResponse>{
        return repo.getDonationByDID(donationId) as LiveData<GetDonationByDonationIDResponse>
    }

    fun changeStatusByDID(repo: CampaignRepo, id: Int, request: ChangeStatusDonationRequest): LiveData<ChangeStatusDonationResponse>{
        return repo.changeStatusByDID(id,request) as LiveData<ChangeStatusDonationResponse>
    }




    //private lateinit var campaigns: MutableLiveData<GetCampaignsResponse>


    // get details of a specific campaign


}