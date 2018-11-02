package com.example.harrisonwjy.charitree.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.harrisonwjy.charitree.model.Campaign
import com.example.harrisonwjy.charitree.model.response.GetCampaignsResponse
import com.example.harrisonwjy.charitree.repo.CampaignRepo

class CampaignViewModel : ViewModel(){

    // get list of campaigns
    private lateinit var campaigns: LiveData<Campaign>


    fun getListOfCampaigns(repo: CampaignRepo): LiveData<GetCampaignsResponse> {
        return repo.showAll() as LiveData<GetCampaignsResponse>


        //return repo.showAll() as LiveData<GetCampaignsResponse>
    }


    //private lateinit var campaigns: MutableLiveData<GetCampaignsResponse>


    // get details of a specific campaign


}