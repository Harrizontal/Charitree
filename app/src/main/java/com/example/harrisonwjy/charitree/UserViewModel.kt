package com.example.harrisonwjy.charitree

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.LoginRequest
import com.example.harrisonwjy.charitree.model.LoginResponse
import com.example.harrisonwjy.charitree.model.Project
import com.example.harrisonwjy.charitree.model.User
import java.util.*

class UserViewModel(val repo : UserRepositoryImpl) : ViewModel() {


    // just for testing
    fun sayHello() = "${repo.giveHello()}"


    /*
    Parameter: LoginRequest object (contains email and password)
    Return: Return a LoginResponse (contains status and user_token) data
     */
    fun authenticate(loginRequest: LoginRequest): LiveData<LoginResponse>{
         return repo.login(loginRequest)
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