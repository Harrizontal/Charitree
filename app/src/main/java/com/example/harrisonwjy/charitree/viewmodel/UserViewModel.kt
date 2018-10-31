package com.example.harrisonwjy.charitree.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.Request
import com.example.harrisonwjy.charitree.model.response.Login
import com.example.harrisonwjy.charitree.model.response.LoginResponse
import com.example.harrisonwjy.charitree.model.response.UserRegister
import com.example.harrisonwjy.charitree.repo.IAuthentication
import com.example.harrisonwjy.charitree.repo.ICampaign

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
    fun authenticate(repo: IAuthentication,request: Request) : LiveData<Login>{
        //val data = MutableLiveData<LoginResponse>()
        return repo.verify(request) as LiveData<Login>
    }

    fun register(repo: ICampaign, request: Request) : LiveData<UserRegister>{
        //val data = MutableLiveData<LoginResponse>()
        return repo.register(request) as LiveData<UserRegister>
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

