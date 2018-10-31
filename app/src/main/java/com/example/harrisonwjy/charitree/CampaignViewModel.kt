package com.example.harrisonwjy.charitree

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.Request
import com.example.harrisonwjy.charitree.model.response.LoginResponse
import com.example.harrisonwjy.charitree.model.request.RegisterCM
import com.example.harrisonwjy.charitree.model.response.CMRegister
import com.example.harrisonwjy.charitree.model.response.CMVerify
import com.example.harrisonwjy.charitree.repo.*


class CampaignViewModel : ViewModel() {
    private var verifyCMResponse = MutableLiveData<CMVerify>()
    private var isCampaignManager = MutableLiveData<Boolean>()



    fun register(repo: ICampaign,request: RegisterCM): LiveData<CMRegister>{
        return repo.register(request) as LiveData<CMRegister>
    }

//    fun verifyCM(repo: ICampaign,request: Request){
//        val results = repo.verify(request) as LiveData<CMVerify>
//        if(results.value!!.isValidResponse == true){
//            isCampaignManager.value = true
//        }else{
//            isCampaignManager.value = false
//        }
//    }

    fun getCampaignManagerAccess(repo: ICampaign,request: Request) : LiveData<CMVerify>? {
        return repo.verify(request) as LiveData<CMVerify>
    }
}

