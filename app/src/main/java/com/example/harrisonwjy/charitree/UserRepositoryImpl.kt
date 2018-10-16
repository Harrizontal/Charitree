package com.example.harrisonwjy.charitree

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.harrisonwjy.charitree.model.Project
import com.example.harrisonwjy.charitree.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class UserRepositoryImpl {

    private val api: CharitreeApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(CharitreeApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(CharitreeApi::class.java)
    }

    fun giveHello(): String = "Hello Kion"

    fun login(email: String, password: String): LiveData<User> {
        val data = MutableLiveData<User>()

        api.login(email,password).enqueue(
                object: Callback<User> {
                    override fun onResponse(call: Call<User>?, response: Response<User>?) {
                        if(response!!.isSuccessful){
                            data.value = response!!.body()
                            Log.e("Login","Successful: "+response!!.body().user_token)
                        }else{
                            data.value = null;
                        }


                    }

                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        data.value = null

                    }

                }
        )
        return data
    }

    fun getHarrizontalProjectList(): LiveData<List<Project>> {
        val data = MutableLiveData<List<Project>>()

        api.getProjectList("Harrizontal").enqueue(
                object : Callback<List<Project>> {
                    override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
                        data.setValue(response.body())
                    }

                    override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                        // TODO better error handling in part #2 ...
                        data.setValue(null)
                    }
                })

        return data
    }

    fun getProjectDetails(userID: String, projectName: String): LiveData<Project> {

        Log.e("DoGoodRepository","DoGoodRepository getProjectDetails accessed")
        val data = MutableLiveData<Project>()

        api.getProjectDetails(userID, projectName).enqueue(
                object : Callback<Project> {
                    override fun onResponse(call: Call<Project>, response: Response<Project>) {
                        data.setValue(response.body())
                        Log.e("DoGoodRepository","DoGoodRepository getProjectDetails pass")
                    }

                    override fun onFailure(call: Call<Project>, t: Throwable) {
                        // TODO better error handling in part #2 ...
                        data.setValue(null)
                        Log.e("DoGoodRepository","DoGoodRepository getProjectDetails fail")
                    }
                })

        return data
    }




}