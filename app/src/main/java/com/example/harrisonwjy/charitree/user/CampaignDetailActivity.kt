package com.example.harrisonwjy.charitree.user

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.model.AcceptedItem
import com.example.harrisonwjy.charitree.model.Campaign
import com.example.harrisonwjy.charitree.user.createdonation.CreateDonationActivity

import kotlinx.android.synthetic.main.activity_campaign_detail.*
import kotlinx.android.synthetic.main.content_campaign_detail.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CampaignDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_detail)
        setSupportActionBar(toolbar)


        //val message = intent.getStringExtra("test1")
        val campaign = intent.getSerializableExtra("campaign") as Campaign
        supportActionBar?.title = campaign.name
        Log.e("CampaignDetail","The campaign name is "+campaign.name)

        displayCampaignImage(campaign.id!!,image_displayed)

        textView_CampName.text = campaign.name

        val timing: String = campaign.start_time.toString() + "00HRS -" + campaign.end_time.toString()+"00HRS"
        textView_time.text = timing
        textView_description.text = campaign.description

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val startDate = LocalDate.parse(campaign.start_date, DateTimeFormatter.ISO_DATE)
            val endDate = LocalDate.parse(campaign.end_date, DateTimeFormatter.ISO_DATE)
            val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")
            textView_startDate.text = startDate.format(formatter)
            textView_endDate.text = endDate.format(formatter)

        }else{
            textView_startDate.text = campaign.start_date
            textView_endDate.text = campaign.end_date
        }



        var itemNeeded: String? = ""

        for (item in campaign.accepted_items as ArrayList<AcceptedItem>){
            Log.e("CIF",item.value + "->"+itemNeeded)
            itemNeeded = item.value + " "+ itemNeeded
        }
        textView_items.text = itemNeeded
        val address = campaign.collection_point + " Singapore "+campaign.postal_code
        textView_address.text = address



//        ViewDonation.setOnClickListener(object: View.OnClickListener {
//            override fun onClick(view: View): Unit {
//
////                val intent = Intent(this, DonarList::class.java)
////                startActivity(intent)
//            }
//        })

        donateButton.setOnClickListener {
            val intent = Intent(this,CreateDonationActivity::class.java).apply{
                putExtra("campaign",campaign)
            }
            startActivity(intent)
        }
    }

    fun displayCampaignImage(id: Int, imageView: ImageView){
        val number = id % 8
        var image: Int? = null
        when(number){
            0 -> {
                image = R.drawable.android_image_0
            }
            1 -> {
                image = R.drawable.android_image_1
            }
            2 -> {
                image = R.drawable.android_image_2
            }
            3 -> {
                image = R.drawable.android_image_3
            }
            4 -> {
                image = R.drawable.android_image_4
            }
            5 -> {
                image = R.drawable.android_image_5
            }
            6 -> {
                image = R.drawable.android_image_6
            }
            7 -> {
                image = R.drawable.android_image_7
            }
        }
        imageView.setImageResource(image!!)
    }

}
