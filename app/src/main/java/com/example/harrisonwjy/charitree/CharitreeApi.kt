package com.example.harrisonwjy.charitree

import com.example.harrisonwjy.charitree.model.Project
import com.example.harrisonwjy.charitree.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface CharitreeApi {



    @GET("users/{user}/repos")
    fun getProjectList(@Path("user") user: String)
            : Call<List<Project>>

    @GET("/repos/{user}/{reponame}")
    fun getProjectDetails(@Path("user") user: String,
                          @Path("reponame") projectName: String)
            : Call<Project>

    @POST("user/create")
    @FormUrlEncoded
    fun savePost(@Field("email") title: String,
                 @Field("password") body: String)
                 : Call<User>

    @POST("user/authenticate")
    @FormUrlEncoded
    fun login(@Field("email") email: String,
              @Field("password") password: String)
                : Call<User>


    companion object {
        //val API_URL = "https://api.github.com/"
        //val API_URL = "http://127.0.0.1/charitree-server/public/"
        val API_URL = "http://10.0.2.2/charitree-server/public/"

    }





}