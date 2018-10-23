package com.example.harrisonwjy.charitree

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.Request
import com.example.harrisonwjy.charitree.model.LoginResponse
import com.example.harrisonwjy.charitree.model.request.RegisterCM
import com.example.harrisonwjy.charitree.repo.IAuthentication
import com.example.harrisonwjy.charitree.repo.ICampaign
import com.example.harrisonwjy.charitree.repo.IRegister
import com.example.harrisonwjy.charitree.repo.IRepository

class UserViewModel : ViewModel() {


    //authService

    // just for testing
    //fun sayHello() = "${repo.giveHello()}"




    /*
    Parameter: Request object (contains email and password)
    Return: Return a LoginResponse (contains status and user_token) data
     */
//    fun authenticate(request: Request): LiveData<LoginResponse>{
//         return repo!!.verify(request)
//    }
    fun authenticate(repo: IAuthentication,request: Request) : LiveData<LoginResponse>{
        //val data = MutableLiveData<LoginResponse>()
        return repo.verify(request) as LiveData<LoginResponse>
    }

    fun register(repo: ICampaign, request: Request) : LiveData<LoginResponse>{
        //val data = MutableLiveData<LoginResponse>()
        return repo.register(request) as LiveData<LoginResponse>
    }


//    private val addressInput = MutableLiveData<User>()


//    val result = Transformations.switchMap(_data) {
//        repo.getHarrizontalProjectList()
//    }
//
//    fun getProjectDetail(): LiveData<String> {
//        val simpleData: LiveData<String> = Transformations.map(repo.getProjectDetails("Harrizontal","eventhou")){
//            input -> input.name + input.full_name
//        }
//        return simpleData
//    }

}

