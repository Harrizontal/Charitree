package com.example.harrisonwjy.charitree.user

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harrisonwjy.charitree.R

class CampaignItemFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_campaign_item, container, false)
        val name = arguments?.getString("message") ?: "Ali Connors"

        val cardview = view.findViewById<CardView>(R.id.cardView)

        cardview.setOnClickListener {
            val intent = Intent(activity,CampaignDetailActivity::class.java)
            startActivity(intent)
        }

        //val button = view.findViewById<Button>(R.id.button2)

        //button.setText(name)

//        button.setOnClickListener{
//            val bundle = bundleOf("userName" to name)
//            Navigation.findNavController.findNavController(button).navigate(
//                    R.id.action_do_good_to_detailsFragment,
//                    bundle)
//        }
        //val bundle = bundleOf("userName" to name)
//        cardView?.setOnClickListener {
//            //Navigation.findNavController(it).navigate(R.id.action_do_good_to_detailsFragment, bundle)
//        }

        return view
    }
}