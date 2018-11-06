package com.example.harrisonwjy.charitree.campaignmanager

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.helper.CreatedCampaignRecyclerAdapter
import com.example.harrisonwjy.charitree.helper.ItemPagerAdapter
import com.example.harrisonwjy.charitree.model.Campaign
import com.example.harrisonwjy.charitree.repo.CampaignRepo
import com.example.harrisonwjy.charitree.user.createdonation.IOnFocusListenable
import com.example.harrisonwjy.charitree.viewmodel.CampaignViewModel
import kotlinx.android.synthetic.main.fragment_created_campaigns.*
import org.koin.android.viewmodel.ext.android.viewModel


class CreatedCampaignsFragment : Fragment() , IOnFocusListenable {


    private lateinit var viewPager: ViewPager
    val campaignViewModel : CampaignViewModel by viewModel()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CreatedCampaignRecyclerAdapter.ViewHolder>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_created_campaigns, container, false)
        //var data = arrayOf("page1", "page2","page3","page4")
        // Inflate the layout for this fragment

        //val obj_adapter = ItemPagerAdapter(childFragmentManager,data)
        //viewPager.adapter=obj_adapter

        val recycler_view = view.findViewById<RecyclerView>(R.id.recycler_view)

        layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager



        // get token
        val prefs = getActivity()!!.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val token: String = prefs.getString("token", "")//"No name defined" is the default value.
        val email: String = prefs.getString("email","")

        campaignViewModel.getListOfCampaignsByCMSession(CampaignRepo(email,token)).observe(this,android.arch.lifecycle.Observer{
            if(it?.status == 1){
                val campaigns : ArrayList<Campaign>? = it.campaigns
                adapter = CreatedCampaignRecyclerAdapter(campaigns)
                recycler_view.adapter = adapter
                emptyState.visibility = View.INVISIBLE
//                val obj_adapter = ItemPagerAdapter(childFragmentManager,campaigns)
//                viewPager.adapter=obj_adapter
            }
        })

        return view
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        //Log.e("CCF", "onWindowFocus called")
        if(hasFocus) {
            val prefs = getActivity()!!.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            val token: String = prefs.getString("token", "")//"No name defined" is the default value.
            val email: String = prefs.getString("email", "")
            campaignViewModel.getListOfCampaignsByCMSession(CampaignRepo(email, token)).observe(this, android.arch.lifecycle.Observer {
                if (it?.status == 1) {
                    val campaigns: ArrayList<Campaign>? = it.campaigns
                    adapter = CreatedCampaignRecyclerAdapter(campaigns)
                    recycler_view.adapter = adapter
                    //emptyState.visibility = View.INVISIBLE
//                val obj_adapter = ItemPagerAdapter(childFragmentManager,campaigns)
//                viewPager.adapter=obj_adapter
                }
            })
        }
    }





    companion object {
        fun newInstance(): CreatedCampaignsFragment {
            return CreatedCampaignsFragment()
        }

    }
}
