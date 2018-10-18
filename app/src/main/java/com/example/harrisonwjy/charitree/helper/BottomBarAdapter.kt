package com.example.harrisonwjy.charitree.helper

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

class BottomBarAdapter(fragmentManager: FragmentManager) : SmartFragmentStatePagerAdapter(fragmentManager) {
    private val fragments = ArrayList<Fragment>()

    // Our custom method that populates this Adapter with Fragments
    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }
}