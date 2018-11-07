package com.example.harrisonwjy.charitree.views.createdonation

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.harrisonwjy.charitree.R
import android.support.v7.widget.DividerItemDecoration
import android.widget.Button
import com.example.harrisonwjy.charitree.helper.*
import kotlin.collections.ArrayList
import android.widget.TextView
import android.widget.Toast
import com.example.harrisonwjy.charitree.model.*
import com.example.harrisonwjy.charitree.model.request.CreateDonationRequest
import com.example.harrisonwjy.charitree.repo.CampaignRepo
import com.example.harrisonwjy.charitree.views.createdonation.address.CreateAddressActivity
import com.example.harrisonwjy.charitree.viewmodel.CampaignViewModel
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A placeholder fragment containing a simple view.
 */
class ChooseAddressFragment : Fragment(), IOnFocusListenable {


    private lateinit var linearLayoutManager: LinearLayoutManager
    private var campaignData: Campaign? = null
    private var selectedItems: ArrayList<CampaignItems> = ArrayList()
    private var timeSlots: ArrayList<TimeSlot>? = null
    private var listOfDates :ArrayList<DateItem>? = null
    private var selectedAddress: ArrayList<Address>? = ArrayList()
    private lateinit var mAdapter: ChooseAddressAdapter
    private lateinit var dateTimeAdapter: BottomBarAdapter
    private lateinit var selectionList: RecyclerView
    private var savedState: Bundle? = null
    val campaignViewModel : CampaignViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("CDTF","CDTF opened")
        val view =  inflater.inflate(R.layout.fragment_create_donation_address, container, false)

        // set Campaign data
        campaignData = arguments?.getSerializable("campaign") as Campaign

        // set button
        val nextButton = view.findViewById<Button>(R.id.nextButton)
        val noItemSelected = view.findViewById<TextView>(R.id.noItemSelected)

        // setting up recycler view
        selectionList = view.findViewById<RecyclerView>(R.id.selection_list)
        linearLayoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(selectionList.getContext(), linearLayoutManager.getOrientation())
        selectionList.layoutManager = linearLayoutManager
        selectionList.addItemDecoration(dividerItemDecoration)

        // put inside viewmodel
        val prefs = getActivity()!!.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val token: String = prefs.getString("token", "")//"No name defined" is the default value.
        val email: String = prefs.getString("email","")


         campaignViewModel.getAddress(CampaignRepo(email,token)).observe(this,android.arch.lifecycle.Observer{
            if(it?.status == 1){
                val addresses : ArrayList<Address>? = it.addresses
                mAdapter = ChooseAddressAdapter(addresses!!, object: OnItemCheckAddressListener{
                    override fun onItemCheck(item: Address) {
                        selectedAddress!!.add(item)

                        val size = selectedAddress!!.size
                        when(size){
                            1 -> {
                                nextButton.visibility = View.VISIBLE
                                noItemSelected.visibility = View.INVISIBLE
                            }
                            0 -> {
                                nextButton.visibility = View.INVISIBLE
                                noItemSelected.visibility = View.VISIBLE
                                noItemSelected.text = "No item selected"
                            }
                            else -> {
                                nextButton.visibility = View.INVISIBLE
                                noItemSelected.visibility = View.VISIBLE
                                noItemSelected.text = "Select one address"
                            }
                        }
                    }

                    override fun onItemUncheck(item: Address) {
                        selectedAddress!!.remove(item)

                        val size = selectedAddress!!.size
                        when(size){
                            1 -> {
                                nextButton.visibility = View.VISIBLE
                                noItemSelected.visibility = View.INVISIBLE
                            }
                            0 -> {
                                nextButton.visibility = View.INVISIBLE
                                noItemSelected.visibility = View.VISIBLE
                                noItemSelected.text = "No item selected"
                            }
                            else -> {
                                nextButton.visibility = View.INVISIBLE
                                noItemSelected.visibility = View.VISIBLE
                                noItemSelected.text = "Select one address"
                            }
                        }
                    }

                })
                selectionList.adapter = mAdapter
            }
        })


        val addAddressButton = view.findViewById<TextView>(R.id.addAddressButton)

        addAddressButton.setOnClickListener {
            val intent = Intent(activity, CreateAddressActivity::class.java)
            activity?.startActivity(intent)
        }

        nextButton.setOnClickListener {

            Log.e("CAF","nextButton clicked")
            val campaignId = campaignData!!.id


            val selectedItems = arguments?.getSerializable("currentChoices") as ArrayList<CampaignItems>
            val request = CreateDonationRequest().apply {
                address_id = selectedAddress!!.get(0).id
                pickup_date = arguments?.getString("getDate")
                pickup_time = arguments?.getString("getTimeSlot")
                items = DonationItems().apply {
                    for (item in selectedItems) {
                        keys.add(item.key!!.toInt())
                        values.add(item.quantity!!.toInt())
                    }
                }
            }

            campaignViewModel.createDonation(CampaignRepo(email,token),campaignId!!,request).observe(this,android.arch.lifecycle.Observer{
                Log.e("CAF","campaignViewMode.createDonation status: " +it!!.status)
                if(it!!.status == 1){
                    Log.e("CAF","Donation created!!!!")
                    Toast.makeText(activity, "Your donation request has been submited", Toast.LENGTH_LONG).show()
                    activity!!.finish()
                }
            })

//            // display data here
//            val addressId = selectedAddress!!.get(0).id
//            val pickupTime = arguments?.getString("getTimeSlot")
//            val pickupDate = arguments?.getString("getDate")
//
//
//            Log.e("SUBMIT","campaignId "+campaignData!!.id)
//            Log.e("SUBMIT","addressId "+selectedAddress!!.get(0).id)
//            Log.e("SUBMIT","pickupTime "+arguments?.getString("getTimeSlot"))
//            Log.e("SUBMIT","pickupDate "+arguments?.getString("getDate"))
//            Log.e("SUBMIT","selectedItem size "+ selectedItems.size)
//            for(item in selectedItems){
//                Log.e("SUBMIT",item.key.toString() + " " +item.item_name + " " +item.quantity)
//            }

        }

        return view
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val prefs = getActivity()!!.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val token: String = prefs.getString("token", "")//"No name defined" is the default value.
        val email: String = prefs.getString("email","")

        campaignViewModel.getAddress(CampaignRepo(email,token)).observe(this,android.arch.lifecycle.Observer{
            val nextButton = view!!.findViewById<Button>(R.id.nextButton)
            val noItemSelected = view!!.findViewById<TextView>(R.id.noItemSelected)
            if(it?.status == 1){
                val addresses : ArrayList<Address>? = it.addresses
                mAdapter = ChooseAddressAdapter(addresses!!, object: OnItemCheckAddressListener{
                    override fun onItemCheck(item: Address) {
                        selectedAddress!!.add(item)

                        val size = selectedAddress!!.size
                        when(size){
                            1 -> {
                                nextButton.visibility = View.VISIBLE
                                noItemSelected.visibility = View.INVISIBLE
                            }
                            0 -> {
                                nextButton.visibility = View.INVISIBLE
                                noItemSelected.visibility = View.VISIBLE
                                noItemSelected.text = "No item selected"
                            }
                            else -> {
                                nextButton.visibility = View.INVISIBLE
                                noItemSelected.visibility = View.VISIBLE
                                noItemSelected.text = "Select one address"
                            }
                        }
                    }

                    override fun onItemUncheck(item: Address) {
                        selectedAddress!!.remove(item)

                        val size = selectedAddress!!.size
                        when(size){
                            1 -> {
                                nextButton.visibility = View.VISIBLE
                                noItemSelected.visibility = View.INVISIBLE
                            }
                            0 -> {
                                nextButton.visibility = View.INVISIBLE
                                noItemSelected.visibility = View.VISIBLE
                                noItemSelected.text = "No item selected"
                            }
                            else -> {
                                nextButton.visibility = View.INVISIBLE
                                noItemSelected.visibility = View.VISIBLE
                                noItemSelected.text = "Select one address"
                            }
                        }
                    }

                })
                selectionList.adapter = mAdapter
            }
        })
    }




    override fun onDestroyView() {
        super.onDestroyView()
        savedState = saveState() /* vstup defined here for sure */


    }

    private fun saveState(): Bundle { /* called either from onDestroyView() or onSaveInstanceState() */
        val state = Bundle()
        //state.putSerializable("selectedItems",selectedItems)
        return state
    }


    companion object {
        fun newInstance(): ChooseAddressFragment {
            return ChooseAddressFragment()
        }
    }
}

interface IOnFocusListenable {
    fun onWindowFocusChanged(hasFocus: Boolean)
}


