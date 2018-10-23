package com.example.harrisonwjy.charitree

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harrisonwjy.charitree.helper.ItemPagerAdapter

import com.example.harrisonwjy.charitree.onboarding.LoginFragment
import kotlinx.android.synthetic.main.fragment_register.*

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



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_campaigns, container, false)
        var data = arrayOf("page1", "page2","page3","page4")
        // Inflate the layout for this fragment

        viewPager = view.findViewById(R.id.viewPager)
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.pageMargin = 20
        val obj_adapter = ItemPagerAdapter(childFragmentManager,data)
        viewPager.adapter=obj_adapter

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): CampaignsFragment {
            return CampaignsFragment()
        }

    }
}
