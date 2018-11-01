package com.example.harrisonwjy.charitree.repo

import com.example.harrisonwjy.charitree.CharitreeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DefaultLogin {


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
    Purpose: To check whether email and password (in a Request object) is valid in the server (or database)
    Parameter: Request object
    Return: LoginResponse data (can be null, or has data
     */
//    fun login(request: Request): LiveData<LoginResponse> {
//        val data = MutableLiveData<LoginResponse>()
//
//        // call login method from CharitreeApi interface
//        api.login(request).enqueue(
//                object: Callback<LoginResponse> {
//                    override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
//                        val loginResponse = LoginResponse()
//                        // If response is successful
//                        /*
//                        isSuccessful is a method of Response
//                         */
//                        if(response!!.isSuccessful){
//
//                            loginResponse.httpStatus = response!!.code()
//                            loginResponse.user_token = response!!.body().user_token
//                            data.value = loginResponse
//                            Log.e("LoginResponse","Successful: "+response!!.body().user_token)
//                        }else{
//                            Log.e("LoginResponse","Not successful. Printing response code: "+response.code())
//                            Log.e("LoginResponse","Not Successful. Printing body: "+response.errorBody().string())
//                            loginResponse.httpStatus = response!!.code()
//                            loginResponse.user_token = null
//                            data.value = loginResponse;
//                        }
//
//
//                    }
//
//                    override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
//                        Log.e("LoginResponse","Unable to submit email and password to API")
//                        data.value = null
//
//                    }
//
//                }
//        )
//        return data
//    }



}