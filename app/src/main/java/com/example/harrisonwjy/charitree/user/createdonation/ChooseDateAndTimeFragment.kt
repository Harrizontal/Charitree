package com.example.harrisonwjy.charitree.user.createdonation

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.model.CampaignItems
import android.support.v7.widget.DividerItemDecoration
import android.widget.Button
import com.example.harrisonwjy.charitree.helper.*
import com.example.harrisonwjy.charitree.model.Campaign
import com.example.harrisonwjy.charitree.model.DateItem
import com.example.harrisonwjy.charitree.model.TimeSlot
import kotlinx.android.synthetic.main.fragment_create_donation_date_time.*
import kotlinx.android.synthetic.main.fragment_create_donation_quantity.*
import java.sql.Time
import android.support.v4.view.ViewCompat.setAlpha
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.support.v4.view.ViewCompat.setAlpha
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.support.v4.view.ViewCompat.setAlpha
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.widget.TextView


/**
 * A placeholder fragment containing a simple view.
 */
class ChooseDateAndTimeFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var campaignData: Campaign? = null
    private var selectedItems: ArrayList<CampaignItems>? = null
    private var timeSlots: ArrayList<TimeSlot>? = null
    private var getTimeSlot: String? = null
    private var listOfDates :ArrayList<DateItem>? = null
    private var getDate: String? = null
    private lateinit var mAdapter: ChooseDateAndTimeAdapter
    private lateinit var dateTimeAdapter: BottomBarAdapter
    private var savedState: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("CDTF","CDTF opened")
        val view =  inflater.inflate(R.layout.fragment_create_donation_date_time, container, false)

        // set Campaign data
        campaignData = arguments?.getSerializable("campaign") as Campaign
        // set selecteditems - for passing to the next fragment
        selectedItems = arguments?.getSerializable("currentChoices") as ArrayList<CampaignItems>

        // timeslots
        listOfDates = loadDateSlots()
        timeSlots = loadData()

        val dateViewPager = view.findViewById<ViewPager>(R.id.dateViewPager)
        dateViewPager.offscreenPageLimit = 3
        dateViewPager.clipToPadding = false
        dateViewPager.pageMargin = 12

        dateViewPager.setPageTransformer(false,object: ViewPager.PageTransformer{
            override fun transformPage(page: View, position: Float) {
                val pageWidth = page.width
                val pageHeight = page.height

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0.9F);
                } else if(position <= 1){ // Page to the left, page centered, page to the right
                    // modify page view animations here for pages in view
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0.9F);
                }
            }
        })

//        dateViewPager.addOnPageChangeListener(object : OnPageChangeListener {
//
//            // This method will be invoked when a new page becomes selected.
//            override fun onPageSelected(position: Int) {
//                Toast.makeText(context,
//                        "Selected page position: $position " + listOfDates!!.get(position).day, Toast.LENGTH_SHORT).show()
//            }
//
//            // This method will be invoked when the current page is scrolled
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                // Code goes here
//            }
//
//            // Called when the scroll state changes:
//            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
//            override fun onPageScrollStateChanged(state: Int) {
//                // Code goes here
//            }
//        })


        val obj_adapter = DateItemAdapter(childFragmentManager,listOfDates)
        dateViewPager.adapter=obj_adapter


        // set button
        val nextButton = view.findViewById<Button>(R.id.nextButton)
        val noItemSelected = view.findViewById<TextView>(R.id.noItemSelected)

        // setting up recycler view
        var selectionList = view.findViewById<RecyclerView>(R.id.selection_list)
        linearLayoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(selectionList.getContext(), linearLayoutManager.getOrientation())
        selectionList.layoutManager = linearLayoutManager
        mAdapter = ChooseDateAndTimeAdapter(timeSlots!!, object: OnItemCheckTimeListener{
            override fun onItemCheck(item: TimeSlot) {
                for(itemData in timeSlots!!){
                    if(itemData.start_time == item.start_time){
                        itemData.checked = true // set quantity to current data set
                    }
                }

                var takeStartDate: Int? = null
                var takeEndDate: Int? = null
                for(itemData in timeSlots!!){

                    if(itemData.checked == true){
                        if(takeStartDate == null){
                            takeStartDate = itemData.start_time
                        }
                        takeEndDate = itemData.end_time
                    }
                }

                if(takeStartDate != null && takeEndDate != null){
                    nextButton.visibility = View.VISIBLE
                    noItemSelected.visibility = View.INVISIBLE
                }else{
                    nextButton.visibility = View.INVISIBLE
                    noItemSelected.visibility = View.VISIBLE
                }
                getTimeSlot = (takeStartDate.toString() + "00 - " + takeEndDate.toString()+"00")
                Log.e("CDTF","Timeslot: "+takeStartDate + "->" +takeEndDate)
            }

            override fun onItemUncheck(item: TimeSlot) {
                for(itemData in timeSlots!!){
                    if(itemData.start_time == item.start_time){
                        itemData.checked = false // set quantity to current data set
                    }
                }

                var takeStartDate: Int? = null
                var takeEndDate: Int? = null
                for(itemData in timeSlots!!){

                    if(itemData.checked == true){
                        if(takeStartDate == null){
                            takeStartDate = itemData.start_time
                        }
                        takeEndDate = itemData.end_time
                    }
                }

                if(takeStartDate != null && takeEndDate != null){
                    nextButton.visibility = View.VISIBLE
                    noItemSelected.visibility = View.INVISIBLE
                }else{
                    nextButton.visibility = View.INVISIBLE
                    noItemSelected.visibility = View.VISIBLE
                }
                getTimeSlot = (takeStartDate.toString() + "00 - " + takeEndDate.toString()+"00")
                Log.e("CDTF","Timeslot: "+takeStartDate + "->" +takeEndDate)
            }

        })
        selectionList.adapter = mAdapter
        selectionList.addItemDecoration(dividerItemDecoration)

        nextButton.setOnClickListener {

            val day = listOfDates!!.get(dateViewPager.currentItem).day
            val month = listOfDates!!.get(dateViewPager.currentItem).month
            getDate = listOfDates!!.get(dateViewPager.currentItem).actualDate
            val fragment = ChooseAddressFragment()
            val args = Bundle()
//            Log.e("CIF","item size is "+selectedItems.size)
            args.putSerializable("currentChoices",selectedItems)
            args.putSerializable("campaign",campaignData)
            args.putString("getTimeSlot",getTimeSlot)
            args.putString("getDate",getDate)
            fragment.arguments = args

            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, fragment)
                    .addToBackStack("ChooseDateAndTimeFragment")
            fragmentTransaction.commit()
        }

        return view
    }

    fun loadData(): ArrayList<TimeSlot>{

        val tempTimeSlots = ArrayList<TimeSlot>()
        val getStart_time = campaignData!!.start_time
        val getEnd_time = campaignData!!.end_time
        val timeSlots = getEnd_time!!.minus(getStart_time!!).minus(1)

        for (i in 0..timeSlots){
            tempTimeSlots.add(TimeSlot().apply{
                start_time = getStart_time + i
                end_time = getStart_time + i + 1
            })
        }
        return tempTimeSlots
    }

    fun loadDateSlots(): ArrayList<DateItem>{
        var tempDateItem = ArrayList<DateItem>()
        val datesInRange = ArrayList<Date>()
        val rawStartDate = campaignData!!.start_date
        val rawEndDate = campaignData!!.end_date
        val df = SimpleDateFormat("yyyy-MM-dd")
        val startDate = df.parse(rawStartDate)
        val endDate = df.parse(rawEndDate)
        val startCalendar = GregorianCalendar()
        startCalendar.time = startDate
        val endCalendar = GregorianCalendar()
        endCalendar.time = endDate

        while(startCalendar.before(endCalendar)){
            val result = startCalendar.time
            datesInRange.add(result)
            //Log.e("CDTF","Days are "+result)
            startCalendar.add(Calendar.DATE,1)
        }
        val result = startCalendar.time
        datesInRange.add(result)

        val dayFormat = SimpleDateFormat("dd")
        val monthFormat = SimpleDateFormat("MMM")
        var dateFormat = SimpleDateFormat("YYYY-MM-dd")

        for (item in datesInRange){
            tempDateItem!!.add(DateItem().apply{
                day = dayFormat.format(item).toInt()
                month = monthFormat.format(item)
                actualDate = dateFormat.format(item)
            })
            //Log.e("CDTF","startDate is "+ dayFormat.format(item) + "month is "+monthFormat.format(item))
        }

        return tempDateItem

    }


    override fun onDestroyView() {
        super.onDestroyView()
        savedState = saveState() /* vstup defined here for sure */


    }

    private fun saveState(): Bundle { /* called either from onDestroyView() or onSaveInstanceState() */
        val state = Bundle()
        state.putSerializable("selectedItems",selectedItems)
        return state
    }


    companion object {
        fun newInstance(): ChooseDateAndTimeFragment {
            return ChooseDateAndTimeFragment()
        }
    }
}


