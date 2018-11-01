package com.example.harrisonwjy.charitree.model

class Campaign {
    companion object {
        fun create(): Campaign = Campaign()
    }

    var id: Int? = 0
    var name: String? = null
    var start_date : String? = null
    var end_date: String? = null
    var start_time: Int? = 0
    var cid: Int? = 0
    var campaign_manager: CampaignManager? = null
    var accepted_items: ArrayList<AcceptableItem>? = null

}