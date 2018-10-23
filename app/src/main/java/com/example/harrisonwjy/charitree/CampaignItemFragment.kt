package com.example.harrisonwjy.charitree

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class CampaignItemFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_campaign_item, container, false)
        val name = arguments?.getString("message") ?: "Ali Connors"

        val button = view.findViewById<Button>(R.id.button2)

        button.setText(name)

//        button.setOnClickListener{
//            val bundle = bundleOf("userName" to name)
//            Navigation.findNavController.findNavController(button).navigate(
//                    R.id.action_do_good_to_detailsFragment,
//                    bundle)
//        }
        //val bundle = bundleOf("userName" to name)
        button.findViewById<Button>(R.id.button2)?.setOnClickListener {
            //Navigation.findNavController(it).navigate(R.id.action_do_good_to_detailsFragment, bundle)
        }

        return view
    }
}