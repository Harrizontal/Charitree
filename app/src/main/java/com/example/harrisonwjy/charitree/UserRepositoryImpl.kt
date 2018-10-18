package com.example.harrisonwjy.charitree

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.harrisonwjy.charitree.model.LoginRequest
import com.example.harrisonwjy.charitree.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepositoryImpl {

    private val api: CharitreeApi

    // create a retrofit for the app to call APIs
    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(CharitreeApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(CharitreeApi::class.java)
    }

    // just for testing
    fun giveHello(): String = "Hello Kion"

    /*
    Purpose: To check whether email and password (in a LoginRequest object) is valid in the server (or database)
    Parameter: LoginRequest object
    Return: LoginResponse data (can be null, or has data
     */
    fun login(loginRequest: LoginRequest): LiveData<LoginResponse> {
        val data = MutableLiveData<LoginResponse>()

        // call login method from CharitreeApi interface
        api.login(loginRequest).enqueue(
                object: Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                        val loginResponse = LoginResponse()
                        // If response is successful
                        /*
                        isSuccessful is a method of Response
                         */
                        if(response!!.isSuccessful){

                            loginResponse.status = response!!.code()
                            loginResponse.user_token = response!!.body().user_token
                            data.value = loginResponse
                            Log.e("Login","Successful: "+response!!.body().user_token)
                        }else{
                            Log.e("Login","Not successful. Printing response code: "+response.code())
                            Log.e("Login","Not Successful. Printing body: "+response.errorBody().string())
                            loginResponse.status = response!!.code()
                            loginResponse.user_token = null
                            data.value = loginResponse;
                        }


                    }

                    override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        data.value = null

                    }

                }
        )
        return data
    }

//    fun getHarrizontalProjectList(): LiveData<List<Project>> {
//        val data = MutableLiveData<List<Project>>()
//
//        api.getProjectList("Harrizontal").enqueue(
//                object : Callback<List<Project>> {
//                    override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
//                        data.setValue(response.body())
//                    }
//
//                    override fun onFailure(call: Call<List<Project>>, t: Throwable) {
//                        // TODO better error handling in part #2 ...
//                        data.setValue(null)
//                    }
//                })
//
//        return data
//    }
//
//    fun getProjectDetails(userID: String, projectName: String): LiveData<Project> {
//
//        Log.e("DoGoodRepository","DoGoodRepository getProjectDetails accessed")
//        val data = MutableLiveData<Project>()
//
//        api.getProjectDetails(userID, projectName).enqueue(
//                object : Callback<Project> {
//                    override fun onResponse(call: Call<Project>, response: Response<Project>) {
//                        data.setValue(response.body())
//                        Log.e("DoGoodRepository","DoGoodRepository getProjectDetails pass")
//                    }
//
//                    override fun onFailure(call: Call<Project>, t: Throwable) {
//                        // TODO better error handling in part #2 ...
//                        data.setValue(null)
//                        Log.e("DoGoodRepository","DoGoodRepository getProjectDetails fail")
//                    }
//                })
//
//        return data
//    }




}