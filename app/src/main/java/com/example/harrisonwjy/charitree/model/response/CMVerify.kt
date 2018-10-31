package com.example.harrisonwjy.charitree.model.response

class CMVerify {
    companion object Factory {
        fun create(): CMVerify = CMVerify()
    }
    var status: Int? = 0
    var campaign_manager: CampaignManager? = null
    var errors: Errors? = null
}

class CampaignManager{
    var cid: Int? = 0
    var UEN: String? = null
    var organization_name: String? = null
}