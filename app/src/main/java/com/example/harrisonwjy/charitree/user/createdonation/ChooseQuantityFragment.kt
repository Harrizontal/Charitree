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
import kotlinx.android.synthetic.main.fragment_create_donation.*
import android.support.v7.widget.DividerItemDecoration
import android.content.ClipData.Item
import com.example.harrisonwjy.charitree.helper.OnItemCheckListener





/**
 * A placeholder fragment containing a simple view.
 */
class ChooseQuantityFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("RegisterCMF","test");
        val view =  inflater.inflate(R.layout.fragment_create_donation, container, false)

        val data = ArrayList<CampaignItems>()
        var selectionList = view.findViewById<RecyclerView>(R.id.selection_list)

        data.add(CampaignItems("Newspaer"))
        data.add(CampaignItems("Paper"))
        data.add(CampaignItems("Clothes"))

        val currentItem = ArrayList<CampaignItems>()

        linearLayoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(selectionList.getContext(), linearLayoutManager.getOrientation())
        selectionList.layoutManager = linearLayoutManager

        selectionList.adapter = ChooseItemAdapter(data, object: OnItemCheckListener{
            override fun onItemCheck(item: CampaignItems) {
                Log.e("CIFragment","added item" + item.item_name)
                currentItem.add(item)
                if(currentItem.size > 0){
                    nextButton.visibility = View.VISIBLE
                    noItemSelected.visibility = View.INVISIBLE
                }else{
                    nextButton.visibility = View.INVISIBLE
                    noItemSelected.visibility = View.VISIBLE
                }
            }

            override fun onItemUncheck(item: CampaignItems) {
                Log.e("CIFragment","remove item "+item.item_name)
                currentItem.remove(item)
                if(currentItem.size > 0){
                    nextButton.visibility = View.VISIBLE
                    noItemSelected.visibility = View.INVISIBLE
                }else{
                    nextButton.visibility = View.INVISIBLE
                    noItemSelected.visibility = View.VISIBLE
                }
            }

        })
        selectionList.addItemDecoration(dividerItemDecoration)

        return view
    }

    companion object {
        fun newInstance(): ChooseQuantityFragment {
            return ChooseQuantityFragment()
        }

    }
}


