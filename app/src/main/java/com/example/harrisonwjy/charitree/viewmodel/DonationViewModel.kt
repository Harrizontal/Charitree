package com.example.harrisonwjy.charitree.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.response.GetAllDonationForUserResponse
import com.example.harrisonwjy.charitree.repo.CampaignRepo

class DonationViewModel : ViewModel(){

    // view my donations

    fun getAllDonationForUser(repo: CampaignRepo): LiveData<GetAllDonationForUserResponse> {
        return repo.showAllUserDonations() as LiveData<GetAllDonationForUserResponse>
    }

    // create donation

    // get address

    // add address

}