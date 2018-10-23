package com.example.harrisonwjy.charitree.model

class VerifyCMResponse {
    companion object Factory {
        fun create(): VerifyCMResponse = VerifyCMResponse()
    }
    var status: Int? = 0
    var campaign_manager: CampaignManager? = null
    var errors: ArrayList<String>? = ArrayList()
    var isValidResponse : Boolean? = null
}

class CampaignManager{
    var cid: Int? = 0
    var UEN: String? = null
    var organization_name: String? = null
}