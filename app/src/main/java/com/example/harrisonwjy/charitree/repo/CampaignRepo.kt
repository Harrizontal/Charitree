package com.example.harrisonwjy.charitree.repo

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.harrisonwjy.charitree.CharitreeApi
import com.example.harrisonwjy.charitree.model.CampaignManager
import com.example.harrisonwjy.charitree.model.Errors
import com.example.harrisonwjy.charitree.model.request.*
import com.example.harrisonwjy.charitree.model.response.*
import com.example.harrisonwjy.charitree.repo.interfaces.ICampaign
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


class CampaignRepo(email: String, token: String) : ICampaign {


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
        val getItem: RegisterCMRequest = item as RegisterCMRequest

        val data = MutableLiveData<CMRegisterResponse>()
        // call login method from CharitreeApi interface
        api.registerCM(getItem).enqueue(
                object: Callback<CMRegisterResponse> {
                    val registerResponse = CMRegisterResponse()
                    override fun onResponse(call: Call<CMRegisterResponse>?, response: Response<CMRegisterResponse>?) {

                        if(response!!.isSuccessful){
                            registerResponse.apply {
                                status = response.body().status
                                errors = null
                            }
                            data.value = registerResponse

                            Log.e("RegisterCMRequest","Successful: "+response.body().toString())
                        }else{
                            Log.e("RegisterCMRequest","Not successful. Printing header code: "+response.headers().toString())
                            Log.e("RegisterCMRequest","Not successful. Printing response code: "+response.code())

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

                    override fun onFailure(call: Call<CMRegisterResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
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

        val data = MutableLiveData<CMVerifyResponse>()
        // call login method from CharitreeApi interface
        api.verifyCM().enqueue(
                object: Callback<CMVerifyResponse> {
                    val verifiyResponse = CMVerifyResponse()
                    override fun onResponse(call: Call<CMVerifyResponse>?, response: Response<CMVerifyResponse>?) {

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

                    override fun onFailure(call: Call<CMVerifyResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
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

    override fun showAll(): Any {
        Log.e("CampaignRepo","Accessing showAll method in CampaignRepo")

        val data = MutableLiveData<GetCampaignsResponse>()
        // call login method from CharitreeApi interface
        api.getCampaigns().enqueue(
                object: Callback<GetCampaignsResponse> {
                    val campaignsResponse = GetCampaignsResponse()
                    override fun onResponse(call: Call<GetCampaignsResponse>?, response: Response<GetCampaignsResponse>?) {

                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")

                            data.value = response.body()


                        }else{
                            if(response.code() == 500){
                                campaignsResponse.apply{
                                    status = 0
                                    errors = Errors().apply{
                                        message = "Server error. Please contact adminstrator"
                                    }
                                }
                            }else {

                                val jObjError = JSONObject(response.errorBody().string())

                                val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                campaignsResponse.apply {
                                    status = jObjError.getString("status").toInt()
                                    errors = Errors().apply {
                                        message = getMessage
                                    }
                                }
                            }

                            data.value = campaignsResponse;
                        }
                    }

                    override fun onFailure(call: Call<GetCampaignsResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        campaignsResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = campaignsResponse
                    }
                }
        )
        return data
    }

    override fun showAllByCMSession(): Any {
        val data = MutableLiveData<GetCampaignsByCMSession>()
        api.getCampaignsByCMSession().enqueue(
                object: Callback<GetCampaignsByCMSession> {
                    val campaignsResponse = GetCampaignsByCMSession()
                    override fun onResponse(call: Call<GetCampaignsByCMSession>?, response: Response<GetCampaignsByCMSession>?) {

                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")

                            data.value = response.body()


                        }else{
                            Log.e("CampaignRepo","failed: "+response.errorBody().string())
                                when(response.code()){
                                    500 -> {
                                        campaignsResponse.apply{
                                            status = 0
                                            errors = Errors().apply{
                                                message = "Server error. Please contact adminstrator"
                                            }
                                        }
                                    }
                                    404 -> {
                                        campaignsResponse.apply{
                                            status = 0
                                            errors = Errors().apply{
                                                message = "No created campaign created by you"
                                            }
                                        }
                                    }
                                    else -> {
                                        val jObjError = JSONObject(response.errorBody().string())

                                        val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                        campaignsResponse.apply {
                                            status = jObjError.getString("status").toInt()
                                            errors = Errors().apply {
                                                message = getMessage
                                            }
                                        }
                                    }
                                }
                            }

                            data.value = campaignsResponse;
                        }


                    override fun onFailure(call: Call<GetCampaignsByCMSession>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        campaignsResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = campaignsResponse
                    }
                }
        )
        return data
    }

    override fun showAddress(): Any {
        val data = MutableLiveData<GetAddressResponse>()
        api.getAddresses().enqueue(
                object: Callback<GetAddressResponse> {
                    val campaignsResponse = GetAddressResponse()
                    override fun onResponse(call: Call<GetAddressResponse>?, response: Response<GetAddressResponse>?) {

                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")
                            data.value = response.body()

                        }else{
                            Log.e("CampaignRepo","failed: "+response.errorBody().string())
                            when(response.code()){
                                500 -> {
                                    campaignsResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    campaignsResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No created campaign created by you"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    campaignsResponse.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = campaignsResponse;
                    }


                    override fun onFailure(call: Call<GetAddressResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        campaignsResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = campaignsResponse
                    }
                }
        )
        return data
    }

    override fun createAddress(item: Any): Any {

        val getItem: GetAddressRequest = item as GetAddressRequest

        val data = MutableLiveData<GetAddressResponse>()
        api.createAddresses(getItem).enqueue(
                object: Callback<GetAddressResponse> {
                    val campaignsResponse = GetAddressResponse()
                    override fun onResponse(call: Call<GetAddressResponse>?, response: Response<GetAddressResponse>?) {
                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            data.value = response.body()

                        }else{
                            Log.e("createAddress",response.errorBody().string())
                            when(response.code()){
                                500 -> {
                                    campaignsResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    campaignsResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No address created by you"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    campaignsResponse.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = campaignsResponse;
                    }


                    override fun onFailure(call: Call<GetAddressResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        campaignsResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = campaignsResponse
                    }
                }
        )
        return data
    }

    override fun createDonation(id: Int,item: Any): Any {
        val getItem: CreateDonationRequest = item as CreateDonationRequest
        val data = MutableLiveData<CreateDonationResponse>()
        api.createDonation(id,getItem).enqueue(
                object: Callback<CreateDonationResponse> {
                    val donationResponse = CreateDonationResponse()
                    override fun onResponse(call: Call<CreateDonationResponse>?, response: Response<CreateDonationResponse>?) {
                        Log.e("createDonation","Successful: Response code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            data.value = response.body()

                        }else{
                            Log.e("createDonation","Error: Response code is "+ response!!.code().toString())
                            when(response.code()){
                                500 -> {
                                    donationResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    donationResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No address created by you"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    //Log.e("createDonation",jObjError.toString())
                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    donationResponse.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = donationResponse;
                    }


                    override fun onFailure(call: Call<CreateDonationResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        donationResponse.apply{
                            status = 0
                            errors = null
                        }
                        data.value = donationResponse
                    }
                }
        )
        return data
    }

    override fun showAllUserDonations(): Any {
        val data = MutableLiveData<GetAllDonationForUserResponse>()
        api.getUserDonations().enqueue(
                object: Callback<GetAllDonationForUserResponse> {
                    val userDonationsResponse = GetAllDonationForUserResponse()
                    override fun onResponse(call: Call<GetAllDonationForUserResponse>?, response: Response<GetAllDonationForUserResponse>?) {

                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")
                            data.value = response.body()

                        }else{
                            //Log.e("CampaignRepo","failed: "+response.errorBody().string())
                            when(response.code()){
                                500 -> {
                                    userDonationsResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    userDonationsResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No created donations created by you"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())
                                    
                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    userDonationsResponse.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = userDonationsResponse;
                    }


                    override fun onFailure(call: Call<GetAllDonationForUserResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        userDonationsResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = userDonationsResponse
                    }
                }
        )
        return data
    }

    override fun getOrgNameByUEN(item: Any): Any{
        val uen : String = item.toString()
        val data = MutableLiveData<GetOrgNameUENResponse>()
        api.getOrgNameByUEN(uen).enqueue(
                object: Callback<GetOrgNameUENResponse> {
                    var uenResponse = GetOrgNameUENResponse()
                    override fun onResponse(call: Call<GetOrgNameUENResponse>?, response: Response<GetOrgNameUENResponse>?) {
                        if(response!!.isSuccessful){
                            data.value = response.body()
                        }else{
                            if(response.code() == 500){
                                uenResponse.apply{
                                    status = 0
                                    errors = Errors().apply{
                                        message = "Server error. Please contact adminstrator"
                                    }
                                }
                            }else{
                                val jObjError = JSONObject(response.errorBody().string())
                                val getMessage  = jObjError.optJSONObject("errors")?.optString("message")

                                uenResponse.apply {
                                    status = jObjError.getString("status").toInt()
                                    errors = Errors().apply {
                                        message = getMessage
                                    }
                                }
                            }
                            data.value = uenResponse;
                        }
                    }

                    override fun onFailure(call: Call<GetOrgNameUENResponse>?, t: Throwable?) {
                        uenResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = uenResponse
                    }
                }
        )

        return data
    }

    override fun getItems(): Any {
        val data = MutableLiveData<GetItemsResponse>()
        api.getItems().enqueue(
                object: Callback<GetItemsResponse> {
                    val itemsResponse = GetItemsResponse()
                    override fun onResponse(call: Call<GetItemsResponse>?, response: Response<GetItemsResponse>?) {

                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")
                            data.value = response.body()

                        }else{
                            //Log.e("CampaignRepo","failed: "+response.errorBody().string())
                            when(response.code()){
                                500 -> {
                                    itemsResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    itemsResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No items in database"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    itemsResponse.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = itemsResponse;
                    }


                    override fun onFailure(call: Call<GetItemsResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        itemsResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = itemsResponse
                    }
                }
        )
        return data
    }

    override fun createCampaign(item: Any): Any {
        val getItem: CreateCampaignRequest = item as CreateCampaignRequest
        val data = MutableLiveData<CreateCampaignResponse>()
        api.createCampaign(getItem).enqueue(
                object: Callback<CreateCampaignResponse> {
                    val createCampaignResponse = CreateCampaignResponse()
                    override fun onResponse(call: Call<CreateCampaignResponse>?, response: Response<CreateCampaignResponse>?) {
                        Log.e("createDonation","Successful: Response code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            data.value = response.body()

                        }else{
                            Log.e("createDonation","Error: Response code is "+ response!!.code().toString())
                            when(response.code()){
                                500 -> {
                                    createCampaignResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    createCampaignResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No address created by you"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    //Log.e("createDonation",jObjError.toString())
                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    createCampaignResponse.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = createCampaignResponse;
                    }


                    override fun onFailure(call: Call<CreateCampaignResponse>?, t: Throwable?) {
                        Log.e("createCampaignResponse","Unable to submit email and password to API")
                        createCampaignResponse.apply{
                            status = 0
                            errors = null
                        }
                        data.value = createCampaignResponse
                    }
                }
        )
        return data
    }

    override fun getListOfDonors(item: Any): Any {
        Log.e("getListOfDonors","item is "+item.toString().toInt())
        val campaignId = item.toString().toInt()
        val data = MutableLiveData<GetListOfDonationsByCIDResponse>()
        api.getListOfDonationsByCID(campaignId).enqueue(
                object: Callback<GetListOfDonationsByCIDResponse> {
                    val donorList = GetListOfDonationsByCIDResponse()
                    override fun onResponse(call: Call<GetListOfDonationsByCIDResponse>?, response: Response<GetListOfDonationsByCIDResponse>?) {

                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")
                            Log.e("CampaignRepo",response.body().toString())
                            data.value = response.body()

                        }else{
                            //Log.e("CampaignRepo","failed: "+response.errorBody().string())
                            when(response.code()){
                                500 -> {
                                    donorList.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    donorList.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No donors list for this campaign in database"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    donorList.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = donorList;
                    }


                    override fun onFailure(call: Call<GetListOfDonationsByCIDResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        donorList.apply{
                            status = null
                            errors = null
                        }
                        data.value = donorList
                    }
                }
        )
        return data
    }

    override fun getDonationByDID(item: Any): Any {
        val donationId = item.toString().toInt()
        val data = MutableLiveData<GetDonationByDonationIDResponse>()
        api.getDonationByDID(donationId).enqueue(
                object: Callback<GetDonationByDonationIDResponse> {
                    val donationDetail = GetDonationByDonationIDResponse()
                    override fun onResponse(call: Call<GetDonationByDonationIDResponse>?, response: Response<GetDonationByDonationIDResponse>?) {

                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")
                            Log.e("CampaignRepo",response.body().toString())
                            data.value = response.body()

                        }else{
                            //Log.e("CampaignRepo","failed: "+response.errorBody().string())
                            when(response.code()){
                                500 -> {
                                    donationDetail.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    donationDetail.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No such donation in database"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    donationDetail.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = donationDetail;
                    }


                    override fun onFailure(call: Call<GetDonationByDonationIDResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        donationDetail.apply{
                            status = null
                            errors = null
                        }
                        data.value = donationDetail
                    }
                }
        )
        return data
    }

    override fun changeStatusByDID(id: Int,item: Any): Any {
        val getItem = item as ChangeStatusDonationRequest
        val data = MutableLiveData<ChangeStatusDonationResponse>()
        api.editStatusByDID(id,getItem).enqueue(
                object: Callback<ChangeStatusDonationResponse> {
                    val getStatusResponse = ChangeStatusDonationResponse()
                    override fun onResponse(call: Call<ChangeStatusDonationResponse>?, response: Response<ChangeStatusDonationResponse>?) {

                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")
                            Log.e("CampaignRepo",response.body().toString())
                            data.value = response.body()

                        }else{
                            //Log.e("CampaignRepo","failed: "+response.errorBody().string())
                            when(response.code()){
                                500 -> {
                                    getStatusResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    getStatusResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No such donation in database"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    getStatusResponse.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = getStatusResponse;
                    }


                    override fun onFailure(call: Call<ChangeStatusDonationResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        getStatusResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = getStatusResponse
                    }
                }
        )
        return data
    }

    override fun getDonationsCount(item: Any): Any {
        val getItem = item.toString()
        val data = MutableLiveData<GetDonationsCountResponse>()
        api.getNoOfDonation(getItem).enqueue(
                object: Callback<GetDonationsCountResponse> {
                    val getDonationCountResponse = GetDonationsCountResponse()
                    override fun onResponse(call: Call<GetDonationsCountResponse>?, response: Response<GetDonationsCountResponse>?) {

                        Log.e("CampaignRepo","Resposne code is "+ response!!.code().toString())
                        if(response!!.isSuccessful){
                            Log.e("CampaignRepo","successful call")
                            Log.e("CampaignRepo",response.body().toString())
                            data.value = response.body()

                        }else{
                            //Log.e("CampaignRepo","failed: "+response.errorBody().string())
                            when(response.code()){
                                500 -> {
                                    getDonationCountResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "Server error. Please contact adminstrator"
                                        }
                                    }
                                }
                                404 -> {
                                    getDonationCountResponse.apply{
                                        status = 0
                                        errors = Errors().apply{
                                            message = "No donors list for this campaign in database"
                                        }
                                    }
                                }
                                else -> {
                                    val jObjError = JSONObject(response.errorBody().string())

                                    val getMessage = jObjError.optJSONObject("errors")?.optString("message")

                                    getDonationCountResponse.apply {
                                        status = jObjError.getString("status").toInt()
                                        errors = Errors().apply {
                                            message = getMessage
                                        }
                                    }
                                }
                            }
                        }

                        data.value = getDonationCountResponse;
                    }


                    override fun onFailure(call: Call<GetDonationsCountResponse>?, t: Throwable?) {
                        Log.e("LoginResponse","Unable to submit email and password to API")
                        getDonationCountResponse.apply{
                            status = null
                            errors = null
                        }
                        data.value = getDonationCountResponse
                    }
                }
        )
        return data
    }


}