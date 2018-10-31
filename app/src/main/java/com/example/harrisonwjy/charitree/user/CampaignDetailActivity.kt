package com.example.harrisonwjy.charitree.user

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.user.createdonation.CreateDonationActivity

import kotlinx.android.synthetic.main.activity_campaign_detail.*
import kotlinx.android.synthetic.main.content_campaign_detail.*

class CampaignDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_detail)
        setSupportActionBar(toolbar)

        val message = intent.getStringExtra("test1")
        val getPosition = intent.getIntExtra("message",-1)
        Log.e("CampaignDetail","The message is "+getPosition)

        image_displayed.setImageResource(R.drawable.give_image)
        //textView_CampName.text = "hello"+getPosition

//        ViewDonation.setOnClickListener(object: View.OnClickListener {
//            override fun onClick(view: View): Unit {
//
////                val intent = Intent(this, DonarList::class.java)
////                startActivity(intent)
//            }
//        })

        donateButton.setOnClickListener {
            val intent = Intent(this,CreateDonationActivity::class.java).apply{
                putExtra("campaignId","2")
            }
            startActivity(intent)
        }
    }

}
