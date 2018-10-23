package com.example.harrisonwjy.charitree

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import com.example.harrisonwjy.charitree.model.Request
import com.example.harrisonwjy.charitree.model.LoginResponse
import com.example.harrisonwjy.charitree.model.request.RegisterCM
import android.support.v4.os.HandlerCompat.postDelayed
import com.example.harrisonwjy.charitree.model.VerifyCMResponse
import com.example.harrisonwjy.charitree.repo.*
import java.util.*


class CampaignViewModel : ViewModel() {
    private var verifyCMResponse = MutableLiveData<VerifyCMResponse>()
    private var isCampaignManager = MutableLiveData<Boolean>()



    fun register(repo: ICampaign,request: RegisterCM): LiveData<LoginResponse>{
        return repo.register(request) as LiveData<LoginResponse>
    }

    fun verifyCM(repo: ICampaign,request: Request){
        val results = repo.verify(request) as LiveData<LoginResponse>
        if(results.value!!.isValidResponse == true){
            isCampaignManager.value = true
        }else{
            isCampaignManager.value = false
        }
    }

    fun getCampaignManagerAccess(repo: ICampaign,request: Request) : LiveData<VerifyCMResponse>? {
        return repo.verify(request) as LiveData<VerifyCMResponse>
    }
}

