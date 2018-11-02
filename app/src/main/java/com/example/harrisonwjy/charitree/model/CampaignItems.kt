package com.example.harrisonwjy.charitree.model

class CampaignItems{

    companion object {
        fun create(): CampaignItems = CampaignItems()
    }

    var item_name: String? = null
    var choiceNumber: Int? = null
    var checked: Boolean? = false

}