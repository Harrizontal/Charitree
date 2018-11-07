package com.example.harrisonwjy.charitree.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.request.GetOrgNameUENRequest
import com.example.harrisonwjy.charitree.model.request.LoginRequest
import com.example.harrisonwjy.charitree.model.request.UserRegisterRequest
import com.example.harrisonwjy.charitree.model.response.GetOrgNameUENResponse
import com.example.harrisonwjy.charitree.model.response.GetSessionsResponse
import com.example.harrisonwjy.charitree.model.response.LoginResponse
import com.example.harrisonwjy.charitree.model.response.UserRegisterResponse
import com.example.harrisonwjy.charitree.repo.AuthenticationRepo
import com.example.harrisonwjy.charitree.repo.interfaces.AuthenticationInterface
import com.example.harrisonwjy.charitree.repo.interfaces.IAuthentication
import com.example.harrisonwjy.charitree.repo.interfaces.ILoginAndRegister

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
    fun authenticate(repo: ILoginAndRegister, request: LoginRequest) : LiveData<LoginResponse>{
        //val data = MutableLiveData<LoginResponse>()
        return repo.verify(request) as LiveData<LoginResponse>
    }

    fun register(repo: ILoginAndRegister, request: UserRegisterRequest) : LiveData<UserRegisterResponse>{
        //val data = MutableLiveData<LoginResponse>()
        return repo.register(request) as LiveData<UserRegisterResponse>
    }

    fun authenticateSession(repo: AuthenticationRepo): LiveData<GetSessionsResponse>{
        return repo.verify() as LiveData<GetSessionsResponse>
    }

//    fun getOrgNameByUEN (repo: ILoginAndRegister, request: GetOrgNameUENRequest) : LiveData<GetOrgNameUENResponse>{
//        return repo.getOrgNameByUEN(GetOrgNameUENRequest) as LiveData<GetOrgNameUENResponse>
//    }

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

