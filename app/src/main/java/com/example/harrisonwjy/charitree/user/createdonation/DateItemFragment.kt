package com.example.harrisonwjy.charitree.user.createdonation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.model.AcceptedItem
import com.example.harrisonwjy.charitree.model.Campaign
import kotlinx.android.synthetic.main.fragment_campaign_item.*
import kotlinx.android.synthetic.main.fragment_campaign_item.view.*
import kotlinx.android.synthetic.main.fragment_date_item.*
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import javax.xml.datatype.DatatypeConstants.DAYS

class DateItemFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_date_item, container, false)

        val day = arguments?.getInt("day")
        val month = arguments?.getString("month")

        val dayTextView = view.findViewById<TextView>(R.id.dayTextView)
        val monthTextView = view.findViewById<TextView>(R.id.monthTextView)
        dayTextView.text = day.toString()
        monthTextView.text = month.toString()

        return view
    }
}