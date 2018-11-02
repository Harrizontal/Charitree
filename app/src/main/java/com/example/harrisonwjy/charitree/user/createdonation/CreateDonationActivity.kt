package com.example.harrisonwjy.charitree.user.createdonation

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.model.Campaign

import kotlinx.android.synthetic.main.activity_create_donation.*

class CreateDonationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_donation)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Donation"
        val campaign = intent.getSerializableExtra("campaign") as Campaign



        val bundle = Bundle()
        bundle.putSerializable("campaign",campaign)
        val fragment = ChooseItemsFragment.newInstance()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
                .add(R.id.frame_layout, fragment)
                .commit()


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.e("CreateDonationAct","Saving state")
    }

//    override fun onBackPressed() {
//        val fm = supportFragmentManager
//        for (i in 0 until fm.backStackEntryCount) {
//            fm.popBackStack()
//        }
//    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

}
