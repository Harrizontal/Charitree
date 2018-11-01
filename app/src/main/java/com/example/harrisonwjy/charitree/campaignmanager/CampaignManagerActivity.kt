package com.example.harrisonwjy.charitree.campaignmanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import com.example.harrisonwjy.charitree.model.User

import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.support.v4.app.Fragment
import android.util.Log
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.setting.SettingFragment
import com.example.harrisonwjy.charitree.user.CampaignsFragment
import com.example.harrisonwjy.charitree.viewmodel.AuthViewModel


//fun Context.MainActivity(user: User): Intent {
//    return Intent(this, MainActivity()::class.java).apply {
//        Log.e("MainActivity","user.id is " + user.id)
//        putExtra("INTENT_USER_ID", user.id)
//    }
//}

fun Context.CampaignManagerActivity(user: User): Intent {
    return Intent(this, CampaignManagerActivity::class.java).apply {
        putExtra(INTENT_USER_ID, user.user_token)
    }
}

private val INTENT_USER_ID = "user_token"

class CampaignManagerActivity : AppCompatActivity() {

    val myViewModel: AuthViewModel by viewModel()
    //private lateinit var viewPager: LockableViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_manager)
        //setSupportActionBar(toolbar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        //viewPager = findViewById(R.id.viewPager)

        setSupportActionBar(toolbar)

        val navigation = navigationView

        navigation.setOnNavigationItemSelectedListener(test)


        //Manually displaying the first fragment - one time only
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, CampaignsFragment.newInstance())
        transaction.commit()
    }


    private val test = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.campaign -> {
                    Log.e("CMActivity","Campaigns clicked")
                    toolbar.title = "Campaigns"
                    selectedFragment = CampaignsFragment.newInstance()
                }
                R.id.setting ->{
                    Log.e("CMActivity","Settings clicked")
                    toolbar.title = "Settings"
                    selectedFragment = SettingFragment.newInstance()
                }
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, selectedFragment!!)
            transaction.commit()
        true
    }
}
