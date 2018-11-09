package com.example.harrisonwjy.charitree.views.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import com.example.harrisonwjy.charitree.R
import android.util.Log


fun Context.RegisterCMActivity(): Intent {
    return Intent(this, RegisterCMActivity::class.java).apply {
    }
}

class RegisterCMActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registercm)

        // onboarding
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_frame, RegisterCMFragment.newInstance())
                .commit()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        Log.v("RegisterCMActivity","Stack count is "+ supportFragmentManager.backStackEntryCount)
        return true
    }

}
