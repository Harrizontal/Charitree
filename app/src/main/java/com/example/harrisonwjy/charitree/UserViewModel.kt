package com.example.harrisonwjy.charitree

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.harrisonwjy.charitree.model.Project
import com.example.harrisonwjy.charitree.model.User
import java.util.*

class UserViewModel(val repo : UserRepositoryImpl) : ViewModel() {

    private val _data = MutableLiveData<List<Project>>()

    //fun sayHello() = "${repo.giveHello()} from $this"
    fun sayHello() = "${repo.giveHello()}"


    fun authenticate(email: String, password: String): LiveData<User>{
         return repo.login(email,password)
    }

    private val addressInput = MutableLiveData<User>()


    val result = Transformations.switchMap(_data) {
        repo.getHarrizontalProjectList()
    }

    fun getProjectDetail(): LiveData<String> {
        val simpleData: LiveData<String> = Transformations.map(repo.getProjectDetails("Harrizontal","eventhou")){
            input -> input.name + input.full_name
        }
        return simpleData
    }

}