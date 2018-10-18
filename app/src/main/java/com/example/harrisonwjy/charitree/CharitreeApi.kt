package com.example.harrisonwjy.charitree

import com.example.harrisonwjy.charitree.model.LoginRequest
import com.example.harrisonwjy.charitree.model.LoginResponse
import com.example.harrisonwjy.charitree.model.Project
import com.example.harrisonwjy.charitree.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface CharitreeApi {



//    @GET("users/{user}/repos")
//    fun getProjectList(@Path("user") user: String)
//            : Call<List<Project>>
//
//    @GET("/repos/{user}/{reponame}")
//    fun getProjectDetails(@Path("user") user: String,
//                          @Path("reponame") projectName: String)
//            : Call<Project>

    // create users - not completed
    @POST("user/create")
    @FormUrlEncoded
    fun savePost(@Field("email") title: String,
                 @Field("password") body: String)
                 : Call<User>

    /*
    Purpose: Send a POST request (http:ipaddress/session) to the respective API with a
    header of content-type: application/json and passing in a LoginRequest object in Body, and received a LoginResponse
    Parameter: LoginRequest object
    Return: LoginResponse
     */
    @POST("sessions")
    @Headers("Content-Type: application/json")
    fun login(@Body loginRequest: LoginRequest)
                : Call<LoginResponse>


    companion object {
        // Local server
        //val API_URL = "http://10.0.2.2/charitree-server/public/"

        // IP address
        val API_URL = "http://172.21.148.170/Charitree/public/"
    }





}