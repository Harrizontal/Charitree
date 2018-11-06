package com.example.harrisonwjy.charitree.helper

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.campaignmanager.CreatedCampaignDetailActivity
import com.example.harrisonwjy.charitree.model.Campaign
import com.example.harrisonwjy.charitree.user.createdonation.CreateDonationActivity
import kotlinx.android.synthetic.main.item_created_campaign.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CreatedCampaignRecyclerAdapter constructor(private val campaigns: ArrayList<Campaign>?) : RecyclerView.Adapter<CreatedCampaignRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_created_campaign, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bindItems(campaigns!!.get(i))
    }

    override fun getItemCount(): Int {
        return campaigns!!.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(campaign: Campaign){
            var displayDate: String = ""
            itemView.item_title.text = campaign.name

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val startDate = LocalDate.parse(campaign.start_date, DateTimeFormatter.ISO_DATE)
                val endDate = LocalDate.parse(campaign.end_date, DateTimeFormatter.ISO_DATE)
                val formatter = DateTimeFormatter.ofPattern("dd LLL yyyy")
                displayDate = startDate.format(formatter) + " - " + endDate.format(formatter)
            }else{
                displayDate = campaign.start_date + " - " + campaign.end_date
            }

            itemView.item_detail.text = displayDate
            itemView.Pending_items.text = "12"
            itemView.Ongoing_items.text = "12"
            itemView.Total_items.text = "12"
            itemView.item_image.setImageResource(R.drawable.give_image)

            itemView.setOnClickListener(object: View.OnClickListener {
                override fun onClick(p0: View?) {
                    val context = itemView.context
                    val intent = Intent(context,CreatedCampaignDetailActivity::class.java).apply{
                        putExtra("campaign",campaign)
                    }
                    context.startActivity(intent)
                }
            })


        }

    }


}