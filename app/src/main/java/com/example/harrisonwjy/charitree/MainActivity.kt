package com.example.harrisonwjy.charitree

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.harrisonwjy.charitree.model.User

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.harrisonwjy.charitree.R.id.toolbar
import android.support.annotation.NonNull
import android.R.id.edit
import android.content.SharedPreferences
import com.example.harrisonwjy.charitree.onboarding.OnboardingActivity


//fun Context.MainActivity(user: User): Intent {
//    return Intent(this, MainActivity()::class.java).apply {
//        Log.e("MainActivity","user.id is " + user.id)
//        putExtra("INTENT_USER_ID", user.id)
//    }
//}

fun Context.MainActivity(user: User): Intent {
    return Intent(this, MainActivity::class.java).apply {
        putExtra(INTENT_USER_ID, user.user_token)
    }
}

lateinit var toolbar: ActionBar
private val INTENT_USER_ID = "user_token"

class MainActivity : AppCompatActivity() {

    val myViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        val navigation = navigationView

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       // val tokenId:String = intent.getStringExtra(INTENT_USER_ID) ?: throw IllegalStateException("field $INTENT_USER_ID missing in Intent")

        button.setOnClickListener {

            val pref = applicationContext.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.remove("token"); // will delete key key_name3

            // Save the changes in SharedPreferences
            editor.apply();

            startActivity(OnboardingActivity())
            finish()

        }

//        textview.text = myViewModel.sayHello()
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action ", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.campaign -> {
                toolbar.title = "Campaigns"
                return@OnNavigationItemSelectedListener true
            }
            R.id.donation -> {
                toolbar.title = "Donations"
                return@OnNavigationItemSelectedListener true
            }
            R.id.tree -> {
                toolbar.title = "Tree"
                return@OnNavigationItemSelectedListener true
            }
            R.id.setting -> {
                toolbar.title = "Setting"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
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
