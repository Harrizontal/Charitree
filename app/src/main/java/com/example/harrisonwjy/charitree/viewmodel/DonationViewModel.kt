package com.example.harrisonwjy.charitree.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.response.GetAllDonationForUserResponse
import com.example.harrisonwjy.charitree.model.response.GetDonationsCountResponse
import com.example.harrisonwjy.charitree.repo.CampaignRepo

class DonationViewModel : ViewModel(){

    // view my donations

    fun getAllDonationForUser(repo: CampaignRepo): LiveData<GetAllDonationForUserResponse> {
        return repo.showAllUserDonations() as LiveData<GetAllDonationForUserResponse>
    }

    fun getDonationCount(repo: CampaignRepo, status: String): LiveData<GetDonationsCountResponse>{
        return repo.getDonationsCount(status) as LiveData<GetDonationsCountResponse>
    }

    // create donation

    // get address

    // add address

}