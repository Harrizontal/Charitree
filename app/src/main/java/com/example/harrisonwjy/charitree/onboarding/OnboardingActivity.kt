package com.example.harrisonwjy.charitree.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.example.harrisonwjy.charitree.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_onboarding.*
import android.support.v4.view.ViewCompat



//fun Context.MainActivity(user: User): Intent {
//    return Intent(this, MainActivity()::class.java).apply {
//        Log.e("MainActivity","user.id is " + user.id)
//        putExtra("INTENT_USER_ID", user.id)
//    }
//}
fun Context.OnboardingActivity(): Intent {
    return Intent(this, OnboardingActivity::class.java).apply {
    }
}

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)


        // onboarding
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_frame, GetStartedFragment.newInstance())
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
