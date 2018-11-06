package com.example.harrisonwjy.charitree

import com.example.harrisonwjy.charitree.model.request.*
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

    // get org name based on UEN
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

    // get list of campaigns by session if he is campaign manager
    @GET("campaigns/campaignmanagers")
    @Headers("Content-Type: application/json")
    fun getCampaignsByCMSession(): Call<GetCampaignsByCMSession>

    // get addresses
    @GET("addresses")
    @Headers("Content-Type: application/json")
    fun getAddresses(): Call<GetAddressResponse>

    // add address
    @POST("addresses")
    @Headers("Content-Type: application/json")
    fun createAddresses(@Body request: GetAddressRequest)
            : Call<GetAddressResponse>

    // create donation
    @POST("donations/campaigns/{campaignId}")
    @Headers("Content-Type: application/json")
    fun createDonation(@Path("campaignId") campaignId: Int, @Body request: CreateDonationRequest)
            : Call<CreateDonationResponse>


    // Get all donations by the current user session
    @GET("donations")
    @Headers("Content-Type: application/json")
    fun getUserDonations(): Call<GetAllDonationForUserResponse>

    // create campaign (2ii)
    @POST("campaigns")
    @Headers("Content-Type: application/json")
    fun createCampaign(@Body request: CreateCampaignRequest)
            : Call<CreateCampaignResponse>

    @GET("items")
    @Headers("Content-Type: applicaiton/json")
    fun getItems(): Call<GetItemsResponse>

    // get list of donations by campaign id (3vi)
    @GET("donations/campaignmanagers/campaigns/{campaignId}")
    @Headers("Content-Type: application/json")
    fun getListOfDonationsByCID(@Path("campaignId") campaignId: Int)
            :Call<GetListOfDonationsByCIDResponse>


    // get donations by donation id (3v)
    @GET("donations/{donationId}/campaignmanagers")
    @Headers("Content-Type: application/json")
    fun getDonationByDID(@Path("donationId") donationId: Int)
            :Call<GetDonationByDonationIDResponse>


    //Change status of a donation (3vii)
    @PATCH("donations/{donationId}/campaignmanagers")
    @Headers("Content-Type: application/json")
    fun editStatusByDID(@Path("donationId") donationId: Int, @Body request: ChangeStatusDonationRequest)
            : Call<ChangeStatusDonationResponse>


    // Get number of successful donations by the current user session
//    @GET("donations/count")
//    @Headers("Content-Type: application/json")
//    fun getNoOfDonation(@Path)


    companion object {
        // Local server
        val API_URL = "http://10.0.2.2/public/"
        // School IP address
        //val API_URL = "http://172.21.148.170/Charitree/public/"
        // Tobias IP address
        //val API_URL = "http://10.27.145.99/public/"
    }





}