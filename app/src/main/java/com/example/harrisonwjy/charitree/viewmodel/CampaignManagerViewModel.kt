package com.example.harrisonwjy.charitree.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.request.RegisterCMRequest
import com.example.harrisonwjy.charitree.model.response.CMRegisterResponse
import com.example.harrisonwjy.charitree.model.response.CMVerifyResponse
import com.example.harrisonwjy.charitree.repo.interfaces.ICampaign


class CampaignManagerViewModel : ViewModel() {



    fun register(repo: ICampaign, request: RegisterCMRequest): LiveData<CMRegisterResponse>{
        return repo.register(request) as LiveData<CMRegisterResponse>
    }


    fun getCampaignManagerAccess(repo: ICampaign, request: RegisterCMRequest) : LiveData<CMVerifyResponse>? {
        return repo.verify(request) as LiveData<CMVerifyResponse>
    }


    // get all requested items

    // create campaign

    // view my campaigns (campaigner manger side)

    // view all donations by that specifci campaign

    // view specific donation

    // approve reject, assign volunteers, cancel


}

