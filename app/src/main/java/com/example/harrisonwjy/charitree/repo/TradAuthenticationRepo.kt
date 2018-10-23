package com.example.harrisonwjy.charitree.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.harrisonwjy.charitree.CharitreeApi
import com.example.harrisonwjy.charitree.model.LoginResponse
import com.example.harrisonwjy.charitree.model.Request
import com.example.harrisonwjy.charitree.model.request.RegisterCM
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TradAuthenticationRepo : ICampaign{


    private val api: CharitreeApi

    companion object {
        fun newInstance() : TradAuthenticationRepo{
            return TradAuthenticationRepo()
        }
    }
    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(CharitreeApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(CharitreeApi::class.java)
    }

    override fun verify(item: Any): LiveData<LoginResponse> {

        val getItem: Request = item as Request
        val data = MutableLiveData<LoginResponse>()

        // call login method from CharitreeApi interface
        api.login(getItem).enqueue(
                object: Callback<LoginResponse> {
                    val loginResponse = LoginResponse()
                    override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {

                        // If response is successful
                        /*
                        isSuccessful is a method of Response
                         */
                        if(response!!.isSuccessful){
                            loginResponse.httpStatus = response!!.code()
                            loginResponse.user_token = response!!.body().user_token
                            loginResponse.isValidResponse = true
                            loginResponse.errors = null
                            loginResponse.message = response.body().message
                            data.value = loginResponse
                            Log.e("Login","Successful: "+response!!.body().user_token)
                        }else{
                            Log.e("Login","Not successful. Printing response code: "+response.code())
                            Log.e("Login","Not Successful. Printing body: "+response.errorBody())
                            loginResponse.apply {
                                httpStatus = response.code()
                                isValidResponse = false
                                message = null
                            }

                            when(response.code()){
                                401 -> {
                                    loginResponse.apply{
                                        loginResponse.errors!!.add("You are not logged in")
                                    }
                                }
                                404 ->{
                                    loginResponse.apply{
                                        loginResponse.errors!!.add("Wrong email and/or password")
                                    }
                                }
                                409 -> {
                                    loginResponse.apply{
                                        loginResponse.errors!!.add("You are a campaign manager already")
                                    }
                                }
                                422 -> {
                                    val jObjError = JSONObject(response.errorBody().string())
                                    Log.e("RegisterCM","Not Successful. Status is : " + jObjError.getString("status"))
                                    Log.e("RegisterCM", "Not Successful. Total errors keys is: " + jObjError.getJSONObject("errors").length())
                                    val keys = jObjError.getJSONObject("errors").keys()
                                    var errorMessage : String
                                    while (keys.hasNext()) {
                                        //Log.e("RegisterCM","Key:"+keys.next())
                                        val test = keys.next()
                                        //Log.e("RegisterCM","Key:"+test)
                                        if (test != "message") {
                                            errorMessage = jObjError.getJSONObject("errors").getJSONArray(test).get(0) as String
                                            Log.e("RegisterCM", "Accessing: " + errorMessage)
                                            loginResponse.errors!!.add(errorMessage)
                                        }
                                    }
                                }

                            }

                            data.value = loginResponse;
                        }


                    }

                    override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        loginResponse.httpStatus = null
                        loginResponse.user_token = null
                        loginResponse.isValidResponse = false
                        data.value = loginResponse
                    }
                }
        )
        return data
    }

    override fun register(item: Any): Any {
        val getItem: Request = item as Request
        val data = MutableLiveData<LoginResponse>()

        api.register(getItem).enqueue(
                object: Callback<LoginResponse> {
                    val loginResponse = LoginResponse()
                    override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {

                        // If response is successful
                        /*
                        isSuccessful is a method of Response
                         */
                        if(response!!.isSuccessful){
                            loginResponse.httpStatus = response!!.code()
                            loginResponse.isValidResponse = true
                            loginResponse.errors = null
                            loginResponse.message = response.body().message
                            data.value = loginResponse
                            Log.e("Register","Successful: "+response!!.body().user_token)
                        }else{
                            Log.e("Register","Not successful. Printing response code: "+response.code())
                            Log.e("Register","Not Successful. Printing body: "+response.errorBody())
                            loginResponse.apply {
                                httpStatus = response.code()
                                isValidResponse = false
                                message = null
                            }

                            when(response.code()){
                                401 -> {
                                    loginResponse.apply{
                                        loginResponse.errors!!.add("Authorized access")
                                    }
                                }
                                404 ->{
                                    loginResponse.apply{
                                        loginResponse.errors!!.add("404 not found")
                                    }
                                }
                                409 -> {
                                    loginResponse.apply{
                                        loginResponse.errors!!.add("Conflict")
                                    }
                                }
                                422 -> {
                                    val jObjError = JSONObject(response.errorBody().string())
                                    Log.e("RegisterCM","Not Successful. Status is : " + jObjError.getString("status"))
                                    Log.e("RegisterCM", "Not Successful. Total errors keys is: " + jObjError.getJSONObject("errors").length())
                                    val keys = jObjError.getJSONObject("errors").keys()
                                    var errorMessage : String
                                    while (keys.hasNext()) {
                                        //Log.e("RegisterCM","Key:"+keys.next())
                                        val test = keys.next()
                                        //Log.e("RegisterCM","Key:"+test)
                                        if (test != "message") {
                                            errorMessage = jObjError.getJSONObject("errors").getJSONArray(test).get(0) as String
                                            Log.e("RegisterCM", "Accessing: " + errorMessage)
                                            loginResponse.errors!!.add(errorMessage)
                                        }
                                    }
                                }

                            }

                            data.value = loginResponse;
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        loginResponse.httpStatus = null
                        loginResponse.user_token = null
                        loginResponse.isValidResponse = false
                        data.value = loginResponse
                    }
                }
        )
        return data
    }

}