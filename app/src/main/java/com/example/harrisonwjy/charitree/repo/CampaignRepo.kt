package com.example.harrisonwjy.charitree.repo

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.harrisonwjy.charitree.CharitreeApi
import com.example.harrisonwjy.charitree.model.request.RegisterCM
import com.example.harrisonwjy.charitree.model.response.*
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

        val data = MutableLiveData<CMRegister>()
        // call login method from CharitreeApi interface
        api.registerCM(getItem).enqueue(
                object: Callback<CMRegister> {
                    val registerResponse = CMRegister()
                    override fun onResponse(call: Call<CMRegister>?, response: Response<CMRegister>?) {

                        if(response!!.isSuccessful){
                            registerResponse.apply {
                                status = response.body().status
                                errors = null
                            }
                            data.value = registerResponse

                            Log.e("RegisterCM","Successful: "+response.body().toString())
                        }else{
                            Log.e("RegisterCM","Not successful. Printing header code: "+response.headers().toString())
                            Log.e("RegisterCM","Not successful. Printing response code: "+response.code())

                            if(response.code() == 500){
                                registerResponse.apply{
                                    status = 0
                                    errors = Errors().apply{
                                        message = "Server error. Please contact adminstrator"
                                    }
                                }
                            }else{
                                val jObjError = JSONObject(response.errorBody().string())

                                val uenArray = jObjError.optJSONObject("errors")?.optJSONArray("UEN")
                                val orgNameArray = jObjError.optJSONObject("errors")?.optJSONArray("organization_name")
                                val getMessage  = jObjError.optJSONObject("errors")?.optString("message")

                                registerResponse.apply {
                                    status = jObjError.getString("status").toInt()
                                    errors = Errors().apply {
                                        uenArray.takeIf { it != null }?.apply {
                                            for (i in 0..(uenArray?.length()!!.minus(1))) {
                                                email?.add(uenArray[i].toString())
                                                Log.e("uenArray", uenArray[i].toString())
                                            }
                                        }
                                        orgNameArray.takeIf { it != null }?.apply {
                                            for (i in 0..(orgNameArray?.length()!!.minus(1))) {
                                                password?.add(orgNameArray[i].toString())
                                                Log.e("orgNameArray", orgNameArray[i].toString())
                                            }
                                        }
                                        message = getMessage
                                    }
                                }
                            }
                        }
                        data.value = registerResponse
                    }

                    override fun onFailure(call: Call<CMRegister>?, t: Throwable?) {
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

    override fun verify(item: Any): Any {
        Log.e("CampaignRepo","Accessing verify method in CampaignRepo")

        val data = MutableLiveData<CMVerify>()
        // call login method from CharitreeApi interface
        api.verifyCM().enqueue(
                object: Callback<CMVerify> {
                    val verifiyResponse = CMVerify()
                    override fun onResponse(call: Call<CMVerify>?, response: Response<CMVerify>?) {

                        if(response!!.isSuccessful){
                            verifiyResponse.apply {
                                status = response.body().status
                                errors = null
                                campaign_manager = CampaignManager().apply{
                                    cid = response.body().campaign_manager!!.cid
                                    UEN = response.body().campaign_manager!!.UEN
                                    organization_name = response.body().campaign_manager!!.organization_name
                                }
                            }
                            data.value = verifiyResponse


                        }else{

                            if(response.code() == 500){
                                verifiyResponse.apply{
                                    status = 0
                                    errors = Errors().apply{
                                        message = "Server error. Please contact adminstrator"
                                    }
                                }
                            }else{
                                val jObjError = JSONObject(response.errorBody().string())

                                val getMessage  = jObjError.optJSONObject("errors")?.optString("message")

                                verifiyResponse.apply {
                                    status = jObjError.getString("status").toInt()
                                    errors = Errors().apply {
                                        message = getMessage
                                    }
                                }
                            }
                            data.value = verifiyResponse;
                        }
                    }

                    override fun onFailure(call: Call<CMVerify>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        verifiyResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = verifiyResponse
                    }
                }
        )
        return data
    }


     fun showAll(item: Any): Any {
        Log.e("CampaignRepo","Accessing showAll method in CampaignRepo")

        val data = MutableLiveData<CMVerify>()
        // call login method from CharitreeApi interface
        api.verifyCM().enqueue(
                object: Callback<CMVerify> {
                    val verifiyResponse = CMVerify()
                    override fun onResponse(call: Call<CMVerify>?, response: Response<CMVerify>?) {

                        if(response!!.isSuccessful){
                            verifiyResponse.apply {
                                status = response.body().status
                                errors = null
                                campaign_manager = CampaignManager().apply{
                                    cid = response.body().campaign_manager!!.cid
                                    UEN = response.body().campaign_manager!!.UEN
                                    organization_name = response.body().campaign_manager!!.organization_name
                                }
                            }
                            data.value = verifiyResponse


                        }else{

                            val jObjError = JSONObject(response.errorBody().string())

                            val getMessage  = jObjError.optJSONObject("errors")?.optString("message")

                            verifiyResponse.apply {
                                status = jObjError.getString("status").toInt()
                                errors = Errors().apply {
                                    message = getMessage
                                }
                            }

                            data.value = verifiyResponse;
                        }
                    }

                    override fun onFailure(call: Call<CMVerify>?, t: Throwable?) {
                        Log.e("Login","Unable to submit email and password to API")
                        verifiyResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = verifiyResponse
                    }
                }
        )
        return data
    }

}