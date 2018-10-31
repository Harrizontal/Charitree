package com.example.harrisonwjy.charitree

import com.example.harrisonwjy.charitree.model.Request
import com.example.harrisonwjy.charitree.model.request.RegisterCM
import com.example.harrisonwjy.charitree.model.response.*
import retrofit2.Call
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
    @POST("users")
    @Headers("Content-Type: application/json")
    fun register(@Body request:Request)
                 : Call<UserRegister>

    /*
    Purpose: Send a POST request (http:ipaddress/session) to the respective API with a
    header of content-type: application/json and passing in a Request object in Body, and received a LoginResponse
    Parameter: Request object
    Return: LoginResponse
     */
//    @POST("sessions")
//    @Headers("Content-Type: application/json")
//    fun login(@Body request: Request)
//                : Call<LoginResponse>

    @POST("sessions")
    @Headers("Content-Type: application/json")
    fun login(@Body request: Request)
            : Call<Login>


    @POST("users/campaignmanager")
    @Headers("Content-Type: application/json")
    fun registerCM(@Body request: RegisterCM)
                : Call<CMRegister>

    @GET("users/campaignmanager")
    @Headers("Content-Type: application/json")
    fun verifyCM(): Call<CMVerify>


    companion object {
        // Local server
        val API_URL = "http://10.0.2.2/public/"
        // IP address
        //val API_URL = "http://172.21.148.170/Charitree/public/"
    }





}