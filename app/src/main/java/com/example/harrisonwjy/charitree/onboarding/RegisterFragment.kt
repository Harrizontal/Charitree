package com.example.harrisonwjy.charitree.onboarding

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.harrisonwjy.charitree.R
import kotlinx.android.synthetic.main.fragment_register.*

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RegisterFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RegisterFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {
            val fm = fragmentManager
            if (fm!!.backStackEntryCount > 0) {
                Log.i("RegisterFragment", "popping backstack")
                fm.popBackStack()
            } else {
                Log.i("RegisterFragment", "nothing on backstack, calling super")
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_frame, LoginFragment.newInstance())
                        .addToBackStack("asd")
                fragmentTransaction.commit()
            }
        }
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }

    }
}
