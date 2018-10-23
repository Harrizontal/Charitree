package com.example.harrisonwjy.charitree.repo

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.harrisonwjy.charitree.CharitreeApi
import com.example.harrisonwjy.charitree.model.CampaignManager
import com.example.harrisonwjy.charitree.model.LoginResponse
import com.example.harrisonwjy.charitree.model.Request
import com.example.harrisonwjy.charitree.model.VerifyCMResponse
import com.example.harrisonwjy.charitree.model.request.RegisterCM
import okhttp3.Credentials
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException
import com.google.gson.JsonObject
import okhttp3.ResponseBody




class CampaignRepo(email: String, token: String) : ICampaign{

    private val api: CharitreeApi

    companion object {
        fun newInstance(email: String,token: String) : CampaignRepo{
            return CampaignRepo(email,token)
        }
    }

    init {
        Log.e("asd","email is "+ email + " token is "+token)
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val originalRequest = chain.request()

                val builder = originalRequest.newBuilder().header("Authorization",
                        Credentials.basic(email, token))

                val newRequest = builder.build()
                return chain.proceed(newRequest)
            }
        }).build()

        val retrofit = Retrofit.Builder()
                .baseUrl(CharitreeApi.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(CharitreeApi::class.java)
    }

    override fun register(item: Any): Any {
        Log.e("CampaignRepo","Accessing register method in CampaignRepo")
        val getItem: RegisterCM = item as RegisterCM

        val data = MutableLiveData<LoginResponse>()
        // call login method from CharitreeApi interface
        api.registerCM(getItem).enqueue(
                object: Callback<LoginResponse> {
                    val loginResponse = LoginResponse()
                    override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                        //val result: JSONObject = response!!.body()
                        // If response is successful
                        /*
                        isSuccessful is a method of Response
                         */

                        if(response!!.isSuccessful){
                            loginResponse.apply {
                                httpStatus = response.code()
                                message = response.body().message
                                isValidResponse = true
                            }
                            data.value = loginResponse

                            Log.e("RegisterCM","Successful: "+response.body().toString())
                        }else{
                            Log.e("RegisterCM","Not successful. Printing header code: "+response.headers().toString())
                            Log.e("RegisterCM","Not successful. Printing response code: "+response.code())

                            if(response.errorBody() == null){
                                Log.e("RegisterCM","Not Successful. response.errorBody() is null")
                            }else{
                                Log.e("RegisterCM","Not Successful. response.errorBody() is NOT null")
                                Log.e("RegisterCM","Not Successful. response.errorBody(): " + response.errorBody())

                                loginResponse.apply {
                                    httpStatus = response.code()
                                    isValidResponse = false
                                    message = null
                                }

                                when(response.code()){
                                    401 -> {
                                        loginResponse.apply{
                                            loginResponse.errors!!.add("Unauthroized access. Please contact the adminstrator")
                                        }
                                    }
                                    404 ->{
                                        loginResponse.apply{
                                            loginResponse.errors!!.add("Request failed. Please contact the adminstrator")
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
                                        loginResponse.apply {
                                            httpStatus = response.code()
                                            isValidResponse = false
                                            message = null
                                        }
                                        var errorMessage : String
                                        while (keys.hasNext()) {
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
                            }




                            //Log.e("RegisterCM","Login response for errors: "+loginResponse.isValidResponse)
                            data.value = loginResponse;
                        }


                    }

                    override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        loginResponse.httpStatus = null
                        loginResponse.user_token = null
                        loginResponse.message = "No internet connection"
                        loginResponse.isValidResponse = false
                        data.value = loginResponse
                    }
                }
        )
        return data
    }

    override fun verify(item: Any): Any {
        Log.e("CampaignRepo","Accessing register method in CampaignRepo")

        val data = MutableLiveData<VerifyCMResponse>()
        // call login method from CharitreeApi interface
        api.verifyCM().enqueue(
                object: Callback<VerifyCMResponse> {
                    val verifyCMResponse = VerifyCMResponse()
                    override fun onResponse(call: Call<VerifyCMResponse>?, response: Response<VerifyCMResponse>?) {
                        //val result: JSONObject = response!!.body()
                        // If response is successful
                        /*
                        isSuccessful is a method of Response
                         */

                        //Log.e("Verify","Successful:"+response!!.code())
                        //Log.e("Verify","Successful: "+response!!.body().status)
                        //Log.e("Verify","Successful: "+response!!.body().campaign_manager!!.cid)
                        //Log.e("Verify","Successful: "+response!!.body().campaign_manager!!.UEN)


                        if(response!!.isSuccessful){
                            verifyCMResponse.apply {
                                status = response.code()
                                errors = null
                                campaign_manager = CampaignManager().apply{
                                    cid = response.body().campaign_manager!!.cid
                                    UEN = response.body().campaign_manager!!.UEN
                                    organization_name = response.body().campaign_manager!!.organization_name
                                }

                                isValidResponse = true
                            }
                            data.value = verifyCMResponse


                        }else{
                            Log.e("RegisterCM","Not successful. Printing header code: "+response.headers().toString())
                            Log.e("RegisterCM","Not successful. Printing response code: "+response.code())

                            if(response.errorBody() == null){
                                Log.e("RegisterCM","Not Successful. response.errorBody() is null")
                            }else{
                                Log.e("RegisterCM","Not Successful. response.errorBody() is NOT null")
                                Log.e("RegisterCM","Not Successful. response.errorBody(): " + response.errorBody())

                                verifyCMResponse.apply {
                                    status = response.code()
                                    campaign_manager = null
                                    isValidResponse = false
                                }

                                when(response.code()){
                                    401 -> {
                                        verifyCMResponse.apply{
                                            verifyCMResponse.errors!!.add("Unauthroized access. Please contact the adminstrator")
                                        }
                                    }
                                    403 -> {
                                        verifyCMResponse.apply{
                                            verifyCMResponse.errors!!.add("Forbidden")
                                        }
                                    }
                                    404 ->{
                                        verifyCMResponse.apply{
                                            verifyCMResponse.errors!!.add("Request failed. Please contact the adminstrator")
                                        }
                                    }
                                    409 -> {
                                        verifyCMResponse.apply{
                                            verifyCMResponse.errors!!.add("You are a campaign manager already")
                                        }
                                    }
                                    422 -> {
                                        val jObjError = JSONObject(response.errorBody().string())
                                        Log.e("RegisterCM","Not Successful. Status is : " + jObjError.getString("status"))
                                        Log.e("RegisterCM", "Not Successful. Total errors keys is: " + jObjError.getJSONObject("errors").length())
                                        val keys = jObjError.getJSONObject("errors").keys()
//                                        verifyCMResponse.apply {
//                                            httpStatus = response.code()
//                                            isValidResponse = false
//                                            message = null
//                                        }
                                        var errorMessage : String
                                        while (keys.hasNext()) {
                                            val test = keys.next()
                                            //Log.e("RegisterCM","Key:"+test)
                                            if (test != "message") {
                                                errorMessage = jObjError.getJSONObject("errors").getJSONArray(test).get(0) as String
                                                Log.e("RegisterCM", "Accessing: " + errorMessage)
                                                verifyCMResponse.errors!!.add(errorMessage)
                                            }
                                        }
                                    }
                                }
                            }




                            //Log.e("RegisterCM","Login response for errors: "+loginResponse.isValidResponse)
                            data.value = verifyCMResponse;
                        }


                    }

                    override fun onFailure(call: Call<VerifyCMResponse>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        verifyCMResponse.apply{
                            status = null
                            errors!!.add("Unable to proceed with this request. No internet available")
                            campaign_manager = null
                            isValidResponse = false
                        }
                        data.value = verifyCMResponse
                    }
                }
        )
        return data
    }

}