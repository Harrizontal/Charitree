package com.example.harrisonwjy.charitree

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import com.example.harrisonwjy.charitree.model.User

import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.support.v4.app.Fragment
import android.util.Log
import com.example.harrisonwjy.charitree.helper.BottomBarAdapter
import com.example.harrisonwjy.charitree.helper.LockableViewPager
import com.example.harrisonwjy.charitree.setting.SettingFragment
import android.support.v4.view.ViewPager




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
lateinit var mAdapter: BottomBarAdapter
private val INTENT_USER_ID = "user_token"

class MainActivity : AppCompatActivity() {

    val myViewModel: UserViewModel by viewModel()
    private lateinit var viewPager: LockableViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        viewPager = findViewById(R.id.viewPager)

        setSupportActionBar(toolbar)

        val navigation = navigationView

        mAdapter = BottomBarAdapter(supportFragmentManager)
        mAdapter.addFragment(CampaignsFragment())
        mAdapter.addFragment(DonationsFragment())
        mAdapter.addFragment(TreeFragment())
        mAdapter.addFragment(SettingFragment())
        viewPager.adapter = mAdapter
        //adapter.(adapter)

        viewPager.setSwipeable(false)

        navigation.setOnNavigationItemSelectedListener(test)



        //Manually displaying the first fragment - one time only
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container, CampaignsFragment.newInstance())
//        transaction.commit()
       // val tokenId:String = intent.getStringExtra(INTENT_USER_ID) ?: throw IllegalStateException("field $INTENT_USER_ID missing in Intent")

//        button.setOnClickListener {
//
//            val pref = applicationContext.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//            val editor = pref.edit()
//            editor.remove("token"); // will delete key key_name3
//
//            // Save the changes in SharedPreferences
//            editor.apply();
//
//            startActivity(OnboardingActivity())
//            finish()
//
//        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.e("MainActivity","Gained focus "+hasFocus)
//        val myFragment = supportFragmentManager.findFragmentByTag("SettingFragment")
//        if (myFragment != null && myFragment!!.isVisible() && hasFocus) {
//            if(viewPager.currentItem == 3){
//                // hard code for now....
//
//            }
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.container, SettingFragment.newInstance(),"SettingFragment")
//            transaction.commit()
//        }

        val fragment = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.currentItem)
        // based on the current position you can then cast the page to the correct Fragment class and call some method inside that fragment to reload the data:
        if (viewPager.currentItem == 3) {
//            supportFragmentManager.beginTransaction().remove(fragment).commit()
//            adapter.addFragment(SettingFragment())
            //viewPager.adapter =
            viewPager.adapter = mAdapter
            viewPager.setCurrentItem(3)
            //mAdapter.getItem(3)
            //mAdapter.getItem(3)
            Log.e("MainActiivity","Fragment found")
        }
    }

    private val test = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            var fragmentTag: String? = null
            when (item.itemId) {
                R.id.campaign -> {
                    toolbar.title = "Campaigns"
                    //selectedFragment = CampaignsFragment.newInstance()
                    viewPager.setCurrentItem(0)
                    //fragmentTag = "CampaignsFragment"
                }
                R.id.donation ->{
                    toolbar.title = "Donations"
                    //selectedFragment = DonationsFragment.newInstance()
                    viewPager.setCurrentItem(1)
                    //fragmentTag = "Donations"
                }
                R.id.tree -> {
                    toolbar.title = "Tree"
                    viewPager.setCurrentItem(2)
                    //selectedFragment = TreeFragment.newInstance()
                    //fragmentTag = "TreeFragment"
                }
                R.id.setting -> {
                    toolbar.title = "Setting"
                    viewPager.setCurrentItem(3)
                    selectedFragment = SettingFragment.newInstance()
                    //fragmentTag = "SettingFragment"
                }
            }

//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.container, selectedFragment!!,fragmentTag)
//            transaction.commit()
true
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
