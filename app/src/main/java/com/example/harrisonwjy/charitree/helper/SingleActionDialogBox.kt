package com.example.harrisonwjy.charitree.helper

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log

//Guide on how to build a simple Dialogbox
// https://guides.codepath.com/android/using-dialogfragment#build-dialog

class SingleActionDialogBox : DialogFragment() {

    companion object {
        fun newInstance(title: String, message: String): SingleActionDialogBox {

            val frag = SingleActionDialogBox()
            val args = Bundle()
            args.putString("title",title)
            args.putString("message",message)
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = arguments?.getString("title")
        val message = arguments?.getString("message")

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        if(arguments == null){
            Log.e("DialogBox","No arguments found")
        }else{
            Log.e("DialogBox",arguments.toString())
            Log.e("DialogBox",arguments?.get("title").toString())
        }
        builder.setTitle(arguments?.getString("title"))
        builder.setMessage(arguments?.getString("message"))
        builder.setPositiveButton("Ok"){
            dialog, whichButton ->
            dialog.dismiss()
        }

        return builder.create()
    }

}

