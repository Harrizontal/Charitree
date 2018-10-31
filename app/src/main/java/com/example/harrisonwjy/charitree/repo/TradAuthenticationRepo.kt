package com.example.harrisonwjy.charitree.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.harrisonwjy.charitree.CharitreeApi
import com.example.harrisonwjy.charitree.model.response.LoginResponse
import com.example.harrisonwjy.charitree.model.Request
import com.example.harrisonwjy.charitree.model.response.Errors
import com.example.harrisonwjy.charitree.model.response.Login
import com.example.harrisonwjy.charitree.model.response.UserRegister
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

    override fun verify(item: Any): Any {

        val getItem: Request = item as Request
        val data = MutableLiveData<Login>()

        // call login method from CharitreeApi interface
        api.login(getItem).enqueue(
                object: Callback<Login> {
                    val loginResponse = Login()
                    override fun onResponse(call: Call<Login>?, response: Response<Login>?) {

                        if(response!!.isSuccessful){
                            loginResponse.apply {
                                status = response.body().status
                                user_token = response.body().user_token
                                errors = null
                            }
                            data.value = loginResponse
                            //Log.e("Login","Successful: "+response!!.body().user_token)
                        }else{
                            //Log.e("Login","Not successful. Printing response code: "+response.code())
                            //Log.e("Login","Not Successful. Printing body: "+response.errorBody())
                            if(response.code() == 500){
                                Log.e("TradAuth","Response code is 500")
                                loginResponse.apply{
                                    status = 0
                                    user_token = null
                                    errors = Errors().apply{
                                        message = "Server error. Please contact adminstrator"
                                    }
                                }
                            }else{
                                val jObjError = JSONObject(response.errorBody().string())
                                val emailArray = jObjError.optJSONObject("errors")?.optJSONArray("email")
                                val passwordArray = jObjError.optJSONObject("errors")?.optJSONArray("password")
                                val getMessage  = jObjError.optJSONObject("errors")?.optString("message")

                                loginResponse.apply {
                                    status = jObjError.getString("status").toInt()
                                    user_token = null
                                    errors = Errors().apply {
                                        emailArray.takeIf { it != null }?.apply {
                                            for (i in 0..(emailArray?.length()!!.minus(1))){
                                                email?.add(emailArray[i].toString())
                                                Log.e("emailArray",emailArray[i].toString())
                                            }
                                        }
                                        passwordArray.takeIf { it != null }?.apply{
                                            for (i in 0..(passwordArray?.length()!!.minus(1))){
                                                password?.add(passwordArray[i].toString())
                                                Log.e("passwordArray",passwordArray[i].toString())
                                            }
                                        }
                                        //password = jObjError.getJSONObject("errors")?.getJSONArray("password") as ArrayList<String>
                                        message = getMessage
                                    }
                                }
                            }
                            data.value = loginResponse;
                        }


                    }

                    override fun onFailure(call: Call<Login>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        loginResponse.apply {
                            status = 0
                            user_token = null
                            errors = null
                        }
                        data.value = loginResponse
                    }
                }
        )
        return data
    }

    override fun register(item: Any): Any {
        val getItem: Request = item as Request
        val data = MutableLiveData<UserRegister>()

        api.register(getItem).enqueue(
                object: Callback<UserRegister> {
                    val registerResponse = UserRegister()
                    override fun onResponse(call: Call<UserRegister>?, response: Response<UserRegister>?) {

                        if(response!!.isSuccessful){
                            registerResponse.apply {
                                status = response.body().status
                                errors = null
                            }
                            data.value = registerResponse
                        }else{
                            if(response.code() == 500){
                                registerResponse.apply{
                                    status = 0
                                    errors = Errors().apply{
                                        message = "Server error. Please contact adminstrator"
                                    }
                                }
                            }else{
                                val jObjError = JSONObject(response.errorBody().string())

                                val emailArray = jObjError.optJSONObject("errors")?.optJSONArray("email")
                                val passwordArray = jObjError.optJSONObject("errors")?.optJSONArray("password")
                                val getMessage  = jObjError.optJSONObject("errors")?.optString("message")

                                registerResponse.apply {
                                    status = jObjError.getString("status").toInt()
                                    errors = Errors().apply {
                                        emailArray.takeIf { it != null }?.apply {
                                            for (i in 0..(emailArray?.length()!!.minus(1))){
                                                email?.add(emailArray[i].toString())
                                                Log.e("emailArray",emailArray[i].toString())
                                            }
                                        }
                                        passwordArray.takeIf { it != null }?.apply{
                                            for (i in 0..(passwordArray?.length()!!.minus(1))){
                                                password?.add(passwordArray[i].toString())
                                                Log.e("passwordArray",passwordArray[i].toString())
                                            }
                                        }
                                        message = getMessage
                                    }
                                }
                            }
                            data.value = registerResponse;
                        }
                    }

                    override fun onFailure(call: Call<UserRegister>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        registerResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = registerResponse
                    }
                }
        )
        return data
    }
}