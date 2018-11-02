package com.example.harrisonwjy.charitree

import com.example.harrisonwjy.charitree.model.request.LoginRequest
import com.example.harrisonwjy.charitree.model.request.RegisterCMRequest
import com.example.harrisonwjy.charitree.model.request.UserRegisterRequest
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


    // register
    @POST("users")
    @Headers("Content-Type: application/json")
    fun register(@Body request:UserRegisterRequest)
                 : Call<UserRegisterResponse>

    // login
    @POST("sessions")
    @Headers("Content-Type: application/json")
    fun login(@Body request: LoginRequest)
            : Call<LoginResponse>


    // register as a  campaign manager
    @POST("users/campaignmanagers")
    @Headers("Content-Type: application/json")
    fun registerCM(@Body request: RegisterCMRequest)
                : Call<CMRegisterResponse>

    @GET("uen")
    @Headers("Content-Type: application/json")
    fun getOrgNameByUEN(@Path("uen") uen: String)
                : Call<GetOrgNameUENResponse>

    // verify user is campaign manager
    @GET("users/campaignmanagers")
    @Headers("Content-Type: application/json")
    fun verifyCM(): Call<CMVerifyResponse>

    // get list of campaign
    @GET("campaigns")
    @Headers("Content-Type: application/json")
    fun getCampaigns(): Call<GetCampaignsResponse>


    companion object {
        // Local server
        val API_URL = "http://10.0.2.2/public/"
        // School IP address
        //val API_URL = "http://172.21.148.170/Charitree/public/"
        // Tobias IP address
        //val API_URL = "http://10.27.145.99/public/"

    }





}