package com.example.harrisonwjy.charitree

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import com.example.harrisonwjy.charitree.model.User
import com.example.harrisonwjy.charitree.onboarding.OnboardingActivity
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        scheduleSplashScreen()

//
//        val user = User.create()
//        user.id = 5
//
//
//        startActivity(MainActivity(user))
//        finish()
    }


    private val USER_TOKEN = "user_token"

    private fun scheduleSplashScreen() {
        val splashScreenDuration = getSplashScreenDuration()
        Handler().postDelayed(
                {
                    // After the splash screen duration, route to the right activities
                    val user = User.create()
                    val prefs = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                    val token = prefs.getString("token", "")//"No name defined" is the default value.
                    val mode = prefs.getString("mode","user")
                    Log.e("SplashScreenActivity","the token is "+token + " and the mode is "+mode)
                    user.user_token = token

                    routeToAppropriatePage(user,mode)
                    finish()
                },
                splashScreenDuration
        )
    }

    private fun getSplashScreenDuration(): Long {
        val sp = getPreferences(Context.MODE_PRIVATE)
        val prefKeyFirstLaunch = "pref_first_launch"

        return when(sp.getBoolean(prefKeyFirstLaunch, true)) {
            true -> {
                // If this is the first launch, make it slow (> 3 seconds) and set flag to false
                sp.edit().putBoolean(prefKeyFirstLaunch, false).apply()
                5000
            }
            false -> {
                // If the user has launched the app, make the splash screen fast (<= 1 seconds)
                1000
            }
        }
    }

    private fun routeToAppropriatePage(user: User, mode: String) {
        // Example routing
//        val intent = Intent(,MainActivity()::class.java)
//        intent.putExtra("INTENT_USER_ID",user.id)

        if (mode == "user"){
            when {
                user.user_token == "" -> startActivity(OnboardingActivity())
                else -> startActivity(MainActivity(user))
            }
        }else if(mode == "campaignmanager"){
            startActivity(CampaignManagerActivity(user))
        }else{
            startActivity(MainActivity(user))
        }

    }


}