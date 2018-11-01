package com.example.harrisonwjy.charitree.user.createdonation

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
import com.example.harrisonwjy.charitree.helper.ChooseItemAdapter
import com.example.harrisonwjy.charitree.model.CampaignItems
import kotlinx.android.synthetic.main.fragment_create_donation_items.*
import android.support.v7.widget.DividerItemDecoration
import com.example.harrisonwjy.charitree.helper.OnItemCheckListener





/**
 * A placeholder fragment containing a simple view.
 */
class ChooseQuantityFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("Quantity","test");
        val view =  inflater.inflate(R.layout.fragment_create_donation_quantity, container, false)

//        val currentChoices = arguments!!.getSerializable("currentChoices") as ArrayList<CampaignItems>
//        for(item in currentChoices){
//            Log.e("CQF","Printing -> item: "+item.item_name + "checked" + item.checked)
//        }


        return view
    }

    companion object {
        fun newInstance(): ChooseQuantityFragment {
            return ChooseQuantityFragment()
        }

    }
}


