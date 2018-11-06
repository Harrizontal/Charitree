package com.example.harrisonwjy.charitree.user

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.helper.ItemPagerAdapter
import com.example.harrisonwjy.charitree.model.Campaign
import com.example.harrisonwjy.charitree.repo.CampaignRepo
import com.example.harrisonwjy.charitree.viewmodel.CampaignViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import android.support.v4.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_campaigns.*


// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CampaignsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CampaignsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CampaignsFragment : Fragment() {


    private lateinit var viewPager: ViewPager
    val campaignViewModel : CampaignViewModel by viewModel()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_campaigns, container, false)
        //var data = arrayOf("page1", "page2","page3","page4")
        // Inflate the layout for this fragment

        viewPager = view.findViewById(R.id.viewPager)
        viewPager.offscreenPageLimit = 4
        viewPager.clipToPadding = false
        viewPager.pageMargin = 25
        //val obj_adapter = ItemPagerAdapter(childFragmentManager,data)
        //viewPager.adapter=obj_adapter



        // get token
        val prefs = getActivity()!!.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val token: String = prefs.getString("token", "")//"No name defined" is the default value.
        val email: String = prefs.getString("email","")

        campaignViewModel.getListOfCampaigns(CampaignRepo(email,token)).observe(this,android.arch.lifecycle.Observer{
            if(it?.status == 1){
                val campaigns : ArrayList<Campaign>? = it.campaigns
                val obj_adapter = ItemPagerAdapter(childFragmentManager,campaigns)
                viewPager.adapter=obj_adapter
                emptyState.visibility = View.INVISIBLE
            }else{
                emptyState.visibility = View.VISIBLE
            }
        })

//        val swiperefresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
//        swiperefresh.setOnRefreshListener{
//
//        }


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun setViewPagerToFirst(){
        viewPager.setCurrentItem(0)
        val prefs = getActivity()!!.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        val token: String = prefs.getString("token", "")//"No name defined" is the default value.
        val email: String = prefs.getString("email","")
        campaignViewModel.getListOfCampaigns(CampaignRepo(email,token)).observe(this,android.arch.lifecycle.Observer{
            if(it?.status == 1){
                val campaigns : ArrayList<Campaign>? = it.campaigns
                val obj_adapter = ItemPagerAdapter(childFragmentManager,campaigns)
                viewPager.adapter=obj_adapter
            }
        })
    }

    companion object {
        fun newInstance(): CampaignsFragment {
            return CampaignsFragment()
        }

    }
}
