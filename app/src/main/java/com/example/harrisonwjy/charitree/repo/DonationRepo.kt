package com.example.harrisonwjy.charitree.repo

import com.example.harrisonwjy.charitree.CharitreeApi
import com.example.harrisonwjy.charitree.repo.interfaces.DonationInterface
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class DonationRepo(email: String, token: String) : DonationInterface{
    private val api: CharitreeApi

    companion object {
        fun newInstance(email: String,token: String) : DonationRepo{
            return DonationRepo(email,token)
        }
    }

    init {
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

    // get all donation

    // get donation

    // create donation


}