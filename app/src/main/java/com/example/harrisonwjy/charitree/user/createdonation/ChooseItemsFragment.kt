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
import com.example.harrisonwjy.charitree.onboarding.RegisterFragment
import android.net.http.SslCertificate.saveState
import android.widget.Button


/**
 * A placeholder fragment containing a simple view.
 */
class ChooseItemsFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    val currentItem = ArrayList<Int>()
    private var savedState: Bundle? = null
    var testData = ArrayList<CampaignItems>().apply{
        add(CampaignItems("kappatesting",1,false))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("CIFragment","OnCreateView()")
        val view =  inflater.inflate(R.layout.fragment_create_donation_items, container, false)

        var data = ArrayList<CampaignItems>()
        var selectionList = view.findViewById<RecyclerView>(R.id.selection_list)

        data.add(CampaignItems("Newspaper", 1,false))
        data.add(CampaignItems("Paper", 2,false))
        data.add(CampaignItems("Clothes", 3,false))
        if(savedState != null) {
            //Log.e("CIF","savedState: "+ savedState!!.getIntegerArrayList("currentChoices"))
            //nextButton.visibility = View.VISIBLE
            val selected = savedState!!.getIntegerArrayList("currentChoices")
            for (item in data){
                for(item2 in selected){
                    Log.e("CIF","Access item: "+item.choiceNumber + "item2: "+item2)
                    if(item.choiceNumber.equals(item2)){
                        Log.e("CIF",item.item_name + "should be ticked")
                        item.checked = true
                        break
                    }
                }
            }
        }

       // val persistentVariable = mySavedInstanceState!!.getString("testtest") ?: "asd"
        //Log.e("CIF","Data is "+persistentVariable)

        linearLayoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(selectionList.getContext(), linearLayoutManager.getOrientation())
        selectionList.layoutManager = linearLayoutManager

        selectionList.adapter = ChooseItemAdapter(data, object: OnItemCheckListener{
            override fun onItemCheck(item: CampaignItems) {
                Log.e("CIFragment","added item" + item.item_name)
                currentItem.add(item.choiceNumber)
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
                currentItem.remove(item.choiceNumber)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val test = savedInstanceState?.getInt("checked",0) ?: 0
//        Log.e("CIF","onViewCreated() test is "+test)
        if(savedState != null){
            nextButton.visibility = View.VISIBLE
        }

        nextButton.setOnClickListener {
            val fragment = ChooseQuantityFragment()
            val args = Bundle()
            args.putSerializable("currentChoices","asd")
            fragment.arguments = args
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, fragment)
                    .addToBackStack("ChooseQuantityFragment")
            fragmentTransaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedState = saveState() /* vstup defined here for sure */
    }

    private fun saveState(): Bundle { /* called either from onDestroyView() or onSaveInstanceState() */
        val state = Bundle()
        state.putIntegerArrayList("currentChoices",currentItem)
//        state.putInt("testest",1)
        return state
    }

    companion object {
        fun newInstance(): ChooseItemsFragment {
            return ChooseItemsFragment()
        }

    }
}



