package com.example.harrisonwjy.charitree.user.address

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import com.example.harrisonwjy.charitree.R
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.harrisonwjy.charitree.helper.SingleActionDialogBox
import com.example.harrisonwjy.charitree.model.Address
import com.example.harrisonwjy.charitree.model.request.GetAddressRequest
import com.example.harrisonwjy.charitree.repo.CampaignRepo
import com.example.harrisonwjy.charitree.viewmodel.CampaignViewModel
import org.koin.android.viewmodel.ext.android.viewModel


//fun Context.MainActivity(user: User): Intent {
//    return Intent(this, MainActivity()::class.java).apply {
//        Log.e("MainActivity","user.id is " + user.id)
//        putExtra("INTENT_USER_ID", user.id)
//    }
//}
fun Context.CreateAddressActivity(): Intent {
    return Intent(this, CreateAddressActivity::class.java).apply {
    }
}

class CreateAddressActivity : AppCompatActivity() {

    val campaignViewModel : CampaignViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_address)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        val registerButton = findViewById<Button>(R.id.registerButton)


        registerButton.setOnClickListener {

            val getStreetName = findViewById<EditText>(R.id.input_street_name)
            val getUnit = findViewById<EditText>(R.id.input_unit)
            val getZip = findViewById<EditText>(R.id.input_zip)

            val requestAddress = Address().apply{
                street_name = getStreetName.text.toString()
                unit = getUnit.text.toString()
                zip = getZip.text.toString()
            }
            val request = GetAddressRequest().apply{
                addresses!!.add(requestAddress)
            }

            val prefs = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            val token: String = prefs.getString("token", "")//"No name defined" is the default value.
            val email: String = prefs.getString("email","")


            campaignViewModel.createAddress(CampaignRepo(email,token),request).observe(this,android.arch.lifecycle.Observer {
                //Log.e("LoginFragment","LoginFragment received "+ it?.user_token + " " + it?.status)

                // If user_token contains something
                if(it?.status == 1){
                    Log.e("CAA","Address added successfull")
                    this.finish()
                }else {
                    val dialogBox = SingleActionDialogBox.newInstance("Opps! Something went wrong",it?.errors?.message)
                    dialogBox.showsDialog = true
                }
            })
        }
    }
        //setSupportActionBar(findViewById(R.id.my_toolbar))
        //getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        // onboarding



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        Log.v("RegisterCMActivity","Stack count is "+ supportFragmentManager.backStackEntryCount)
        return true
    }
}
