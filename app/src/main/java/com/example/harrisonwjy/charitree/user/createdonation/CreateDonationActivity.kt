package com.example.harrisonwjy.charitree.user.createdonation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.onboarding.RegisterFragment
import com.example.harrisonwjy.charitree.setting.RegisterCMFragment
import com.example.harrisonwjy.charitree.user.CampaignsFragment

import kotlinx.android.synthetic.main.activity_create_donation.*

class CreateDonationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_donation)

        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, ChooseItemFragment.newInstance())
                .commit()


    }

}
