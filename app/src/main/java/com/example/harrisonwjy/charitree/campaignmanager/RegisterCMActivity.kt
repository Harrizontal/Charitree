package com.example.harrisonwjy.charitree.campaignmanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import com.example.harrisonwjy.charitree.R
import android.util.Log


//fun Context.MainActivity(user: User): Intent {
//    return Intent(this, MainActivity()::class.java).apply {
//        Log.e("MainActivity","user.id is " + user.id)
//        putExtra("INTENT_USER_ID", user.id)
//    }
//}
fun Context.RegisterCMActivity(): Intent {
    return Intent(this, RegisterCMActivity::class.java).apply {
    }
}

class RegisterCMActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registercm)

//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//
//        setSupportActionBar(toolbar)
//        actionBar.setDisplayHomeAsUpEnabled(true)

        //setSupportActionBar(findViewById(R.id.my_toolbar))
        //getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

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

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
