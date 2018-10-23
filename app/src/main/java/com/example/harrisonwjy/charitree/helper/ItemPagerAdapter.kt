package com.example.harrisonwjy.charitree.helper

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.harrisonwjy.charitree.CampaignItemFragment

class ItemPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    var data: Array<String>? = null
    constructor(fragmentManager: FragmentManager, data2: Array<String>) : this(fragmentManager){
        data = data2
    }


    // 2
    override fun getItem(position: Int): Fragment {
        //return MovieFragment.newInstance(movies[position])
        val fragment = CampaignItemFragment()
        val args = Bundle()
        args.putString("message", "Hello this is data  "+data!!.get(position))
        fragment.setArguments(args)
        return fragment
    }

    // 3
    override fun getCount(): Int {
        return data!!.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

}